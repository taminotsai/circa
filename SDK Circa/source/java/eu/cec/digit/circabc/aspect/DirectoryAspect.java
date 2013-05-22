/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.aspect;

import static eu.cec.digit.circabc.model.CircabcModel.TYPE_DIRECTORY_SERVICE;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

/**
 * This class contains the behaviour behind the 'ci:circaBC' aspect. So far this
 * aspect is just used as a tag.
 *
 * @author Clinckart Stephane
 */
public class DirectoryAspect extends AbstractAspect
				implements NodeServicePolicies.OnAddAspectPolicy
{

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.TYPE;
	}

	@Override
	public QName getComparatorQName()
	{
		return TYPE_DIRECTORY_SERVICE;
	}

	/** OwnableService */
	protected OwnableService ownableService;

	/** DictionaryService */
	protected DictionaryService dictionaryService;



	/**
	 * Sets the OwnableService
	 *
	 * @param nodeService
	 *            the node service
	 */
	public void setOwnableService(final OwnableService ownableService) {
		this.ownableService = ownableService;
	}

	/**
	 * Sets the Dictionary component
	 *
	 * @param policyComponent
	 *            the policy component
	 */
	public void setDictionaryService(final DictionaryService dictionaryComponent) {
		this.dictionaryService = dictionaryComponent;
	}

	/**
	 * Spring initilaise method used to register the policy behaviours
	 */
	public void initialise() {
		// Register the policy behaviours
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onAddAspect"),
				getComparatorQName(), new JavaBehaviour(this,
						"onAddAspect"));

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
			// TODO implements here some code
		}
	}

}