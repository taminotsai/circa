/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.aspect;

import static eu.cec.digit.circabc.model.CircabcModel.ASPECT_EVENT;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

/**
 * This class contains the behaviour behind the 'ci:circabcEvent' aspect.
 * So far this aspect is just used as a tag.
 *
 * @author Clinckart Stephane
 */
public class EventAspect extends AbstractAspect implements
		NodeServicePolicies.OnAddAspectPolicy,
		NodeServicePolicies.BeforeDeleteNodePolicy
{


	/**
	 * Spring initialise method used to register the policy behaviours
	 */
	public void initialise()
	{
		// Register the policy behaviours
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onAddAspect"),
				getComparatorQName(), new JavaBehaviour(this, "onAddAspect"));
	}

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.ASPECT;
	}

	@Override
	public QName getComparatorQName()
	{
		return ASPECT_EVENT;
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
}