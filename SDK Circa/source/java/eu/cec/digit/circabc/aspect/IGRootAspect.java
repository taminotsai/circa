/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.aspect;

import static eu.cec.digit.circabc.model.CircabcModel.ASPECT_IGROOT;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implement behavior for IGRoot aspect
 * @author Philippe Dubois
 *
 */
/**
 * @todo add the policy BeforeDeleteNodePolicy to delete the associated groups
 *       and profiles
 */
public class IGRootAspect extends AbstractAspect implements
		NodeServicePolicies.OnAddAspectPolicy,
		NodeServicePolicies.BeforeDeleteNodePolicy
{

	private static final Log logger = LogFactory.getLog(IGRootAspect.class);

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.ASPECT;
	}

	@Override
	public QName getComparatorQName()
	{
		return ASPECT_IGROOT;
	}


	public interface Roles {
		/* non prefixed role */
		public static final String IGLEADER = "IGLEADER";
		public static final String ROLE_DIRADMIN = "ROLE_DIRADMIN";
	}

	public void onAddAspect(final NodeRef nodeRef, final QName aspectTypeQName) {
		if (logger.isTraceEnabled()) {
			logger.trace("Add Aspect:" + getComparatorQName() + " to node:" + nodeRef);
		}
	}

	/**
	 * Spring initialise method used to register the policy behaviours
	 */
	public void initialise() {
		// Register the policy behaviours
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onAddAspect"), getComparatorQName(),
				new JavaBehaviour(this, "onAddAspect"));
		// BeforeDeleteNodePolicy
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "beforeDeleteNode"),
				getComparatorQName(), new JavaBehaviour(this, "beforeDeleteNode"));
	}
}
