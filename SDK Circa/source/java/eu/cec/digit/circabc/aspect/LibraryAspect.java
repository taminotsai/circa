/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.aspect;

import static eu.cec.digit.circabc.model.CircabcModel.ASPECT_LIBRARY;

import java.util.List;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class contains the behaviour behind the 'ci:circaLibraryService' aspect.
 * So far this aspect is just used as a tag.
 *
 * @author Clinckart Stephane
 */
public class LibraryAspect extends AbstractAspect implements
		NodeServicePolicies.OnAddAspectPolicy,
		NodeServicePolicies.BeforeDeleteNodePolicy
{
	private static final Log logger = LogFactory.getLog(LibraryAspect.class);

	/**
	 * Spring initialise method used to register the policy behaviours
	 */
	public void initialise() {
		// Register the policy behaviours
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onAddAspect"),
				getComparatorQName(), new JavaBehaviour(this, "onAddAspect"));
		// BeforeDeleteNodePolicy
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "beforeDeleteNode"),
				getComparatorQName(), new JavaBehaviour(this,
						"beforeDeleteNode"));
	}

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.ASPECT;
	}

	@Override
	public QName getComparatorQName()
	{
		return ASPECT_LIBRARY;
	}

	/**
	 * onAddAspect policy behaviour. No special java code for behavior
	 *
	 * @param nodeRef
	 *            the node reference
	 * @param aspectTypeQName
	 *            the qname of the aspect being applied
	 */
	public void onAddAspect(final NodeRef nodeRef, final QName aspectTypeQName) {

		if (aspectTypeQName.equals(getComparatorQName()) == true) {
		}
	}

	/**
	 * WorkArround for ML bug when deleting a folder who contains a empty_translation
	 */
	@Override
	public void beforeDeleteNode(final NodeRef nodeRef)
	{
		if(nodeService.getType(nodeRef).equals(ContentModel.TYPE_FOLDER)) {
			final List<ChildAssociationRef> childs = nodeService.getChildAssocs(nodeRef);

			for(ChildAssociationRef child : childs) {
				if(nodeService.hasAspect(child.getChildRef(), ContentModel.ASPECT_MULTILINGUAL_EMPTY_TRANSLATION)) {
					if(logger.isTraceEnabled()) {
						logger.trace("Work arround for deleting an Empty Translation");
					}
					nodeService.deleteNode(child.getChildRef());
				}
			}
		}
	}
}