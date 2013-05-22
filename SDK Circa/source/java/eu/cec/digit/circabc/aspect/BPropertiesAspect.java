/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.aspect;

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

import eu.cec.digit.circabc.model.DocumentModel;

/**
 * This class contains the behaviour behind the 'cd:bproperties' aspect.
 *
 * @author patrice.coppens@trasys.lu
 *
 * 06-juil.-07 - 09:18:07
 */
public class BPropertiesAspect extends AbstractAspect implements NodeServicePolicies.OnAddAspectPolicy, NodeServicePolicies.OnCreateNodePolicy
{



	private static final Log logger = LogFactory.getLog(BPropertiesAspect.class);

	/** bean name */
	public static final String NAME = "BPropertiesAspect";


	/**
	 * Spring initilaise method used to register the policy behaviours.
	 */
	@Override
	public void initialise() {

		if (logger.isInfoEnabled())
			logger.info("policy bind (onAddAspect)");

		// Register the policy behaviours
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onAddAspect"),
				getComparatorQName(), new JavaBehaviour(this, "onAddAspect" /*, NotificationFrequency.FIRST_EVENT*/));

		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onCreateNode"),
				ContentModel.TYPE_MULTILINGUAL_CONTAINER, new JavaBehaviour(this, "onCreateNode"));

	}

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.ASPECT;
	}

	@Override
	public QName getComparatorQName()
	{
		return DocumentModel.ASPECT_BPROPERTIES;
	}

	/**
	 * if noderef is a ref to a node mlcontainer, we must remove bproperties
	 * aspect to all mldocument. if noderef is a mldocument, we must remove the
	 * aspect.
	 *
	 * @see eu.cec.digit.circabc.aspect.AbstractAspect#onAddAspect(org.alfresco.service.cmr.repository.NodeRef,
	 *      org.alfresco.service.namespace.QName)
	 * @param nodeRef
	 *            the node.
	 * @param aspectTypeQName
	 *            aspect added
	 */
	public void onAddAspect(final NodeRef nodeRef, final QName aspectTypeQName) {
		// check if exist
		if (!this.nodeService.exists(nodeRef))
			return;

		// check if is a mlcontainer and are correct aspect
		if (nodeService.getType(nodeRef).isMatch(ContentModel.TYPE_MULTILINGUAL_CONTAINER)
				&& aspectTypeQName.equals(getComparatorQName()))
		{
			if (logger.isInfoEnabled())
			{
				logger.info("mlcontainer receive aspect bproperties. We must remove this aspect on his child");
			}

			// check if noderef have child
			if (nodeService.getChildAssocs(nodeRef).size() != 0)
			{

				final List<ChildAssociationRef> docs = nodeService.getChildAssocs(nodeRef);

				for (final ChildAssociationRef ref : docs) {
					// check if has bproperties aspect. Not needed for now, but
					// in futur mlcontainer can have child without bproperties aspect.
					if (nodeService.hasAspect(ref.getChildRef(),
							getComparatorQName()))
					{
						if (logger.isInfoEnabled())
						{
							logger.info("remove bproperties to " + ref.getChildRef().getId());
						}

						nodeService.removeAspect(ref.getChildRef(),	getComparatorQName());
					}
				}
			} else {
				// Not an error ... the mlContainer is perhaps being created
				if (logger.isDebugEnabled())
				{
					logger.debug("mlcontainer has not children yet");
				}

			}
		}
	}

	public void onCreateNode(final ChildAssociationRef ref)
	{

		final NodeRef nodeRef = ref.getChildRef();

		// add CProperties Aspect
		nodeService.addAspect(nodeRef, getComparatorQName(), null);
	}

}
