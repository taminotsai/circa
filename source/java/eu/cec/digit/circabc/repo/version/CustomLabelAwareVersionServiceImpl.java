/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.version;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.repo.version.Version2ServiceImpl;
import org.alfresco.service.cmr.repository.AspectMissingException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.ReservedVersionNameException;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.VersionNumber;

import eu.cec.digit.circabc.model.CircabcModel;

/**
 * Override the default version label calculation behaviour.
 *
 * @author Yanick Pignot
 */
public class CustomLabelAwareVersionServiceImpl extends Version2ServiceImpl
{
	private static final String LABEL_REGEX = "[0-9]+[\\.[0-9]+]+";

	public static final String PROP_CUSTOM_VERSION_LABEL = "customVersionLabel";

	@Override
	protected String invokeCalculateVersionLabel(QName classRef,
			Version preceedingVersion,
			int versionNumber,
			Map<String, Serializable>versionProperties)	{
		
		final String customLabel = (String) versionProperties.get(PROP_CUSTOM_VERSION_LABEL);
		versionProperties.remove(PROP_CUSTOM_VERSION_LABEL);

		if(customLabel == null || customLabel.trim().length() < 1)
		{
			return super.invokeCalculateVersionLabel(classRef, preceedingVersion, versionNumber, versionProperties);
		}
		else if(isValidVersionLabel(customLabel) == false)
		{
			throw new IllegalArgumentException("Invalid value for version label: " + customLabel + ". It must matches " + LABEL_REGEX);
		}
		else if(preceedingVersion != null && isNewLabelValid(preceedingVersion.getVersionLabel(), customLabel) == false)
		{
			throw new IllegalArgumentException("Invalid value for version label: " + customLabel + ". It must be greather than the previous one: " + preceedingVersion.getVersionLabel());
		}
		else
		{
			return customLabel;
		}
	}

	private boolean isNewLabelValid(final String preceedinLabel, final String customLabel) {
		
		final VersionNumber oldLabel = new VersionNumber(preceedinLabel);
		final VersionNumber newLabel = new VersionNumber(customLabel);

		return oldLabel.compareTo(newLabel) < 1;
	}

	public static boolean isValidVersionLabel(final String value) {
		return value != null && value.matches(LABEL_REGEX);
	}
	
	/**
	 * Removes the publish information when versioning the document.
	 * This has to be done because each time we create a new version of a 
	 * document, only the current could have been published. New versions have 
	 * to be republished since they might represent a new document.
	 * 
	 * @see org.alfresco.repo.version.Version2ServiceImpl#createVersion(org.alfresco.service.cmr.repository.NodeRef, java.util.Map)
	 */
	@Override
	public Version createVersion(NodeRef nodeRef,
			Map<String, Serializable> versionProperties)
			throws ReservedVersionNameException, AspectMissingException {
		
		Version version = super.createVersion(nodeRef, versionProperties);
		
		NodeRef versionedNodeRef = version.getVersionedNodeRef();
		
		// Remove aspect and associated properties
		if (nodeService.hasAspect(versionedNodeRef, CircabcModel.ASPECT_EXTERNALLY_PUBLISHED)) {
			nodeService.removeProperty(versionedNodeRef, CircabcModel.PROP_REPOSITORIES_INFO);
			nodeService.removeAspect(versionedNodeRef, CircabcModel.ASPECT_EXTERNALLY_PUBLISHED);
		}
		
		return version;
	}
}
