/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.aspect;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.model.DocumentModel;

/**
 * @author patrice.coppens@trasys.lu
 *
 * 24-juil.-07 - 09:26:59
 */
public class MLDocumentAspect extends AbstractAspect implements
					NodeServicePolicies.OnAddAspectPolicy,
					NodeServicePolicies.BeforeDeleteNodePolicy
{

	private static final Log LOGGER = LogFactory.getLog(MLDocumentAspect.class);

	/**
	 * bean name
	 */
	public static final String NAME= "mLDocumentAspect";

	@Override
	public ComparatorType getComparator()
	{
		return ComparatorType.ASPECT;
	}

	@Override
	public QName getComparatorQName()
	{
		return ContentModel.ASPECT_MULTILINGUAL_DOCUMENT;
	}


    /**
     * Initialise the Multilingual Aspect.
     *
     * Ensures that the {@link ContentModel#ASPECT_MULTILINGUAL_DOCUMENT ml document aspect}
     */

	@Override
    public void initialise()
    {

		if (LOGGER.isInfoEnabled())
			LOGGER.info("policy bind (onAddAspect)");

        this.policyComponent.bindClassBehaviour(
                QName.createQName(NamespaceService.ALFRESCO_URI, "onAddAspect"),
                ContentModel.ASPECT_MULTILINGUAL_DOCUMENT,
                new JavaBehaviour(this, "onAddAspect"));

        this.policyComponent.bindClassBehaviour(
                QName.createQName(NamespaceService.ALFRESCO_URI, "beforeDeleteNode"),
                ContentModel.ASPECT_MULTILINGUAL_EMPTY_TRANSLATION,
                new JavaBehaviour(this, "beforeDeleteNode"));


    }

	public void onAddAspect(final NodeRef nodeRef, final QName aspectTypeQName) {

		//	check if exist
		if (! this.nodeService.exists(nodeRef))
		{
			return;
		}

		nodeService.removeAspect(nodeRef, DocumentModel.ASPECT_BPROPERTIES);
		//add CProperties Aspect
		nodeService.addAspect(nodeRef, DocumentModel.ASPECT_CPROPERTIES, null);

	}

	public void beforeDeleteNode(final NodeRef nodeRef)
	{
		// add temporary aspect to force a complete deletion of the empty translation
		nodeService.addAspect(nodeRef, ContentModel.ASPECT_TEMPORARY, null);

		super.beforeDeleteNode(nodeRef);
	}


}
