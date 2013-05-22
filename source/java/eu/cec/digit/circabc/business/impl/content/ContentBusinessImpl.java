/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.impl.content;


import java.io.File;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.aspect.ContentNotifyAspect;
import eu.cec.digit.circabc.business.api.BusinessStackError;
import eu.cec.digit.circabc.business.api.content.ContentBusinessSrv;
import eu.cec.digit.circabc.business.helper.ContentManager;
import eu.cec.digit.circabc.business.helper.ValidationManager;
import eu.cec.digit.circabc.business.impl.AssertUtils;

/**
 * Business service implementation to upload a document.
 *
 * @author Yanick Pignot
 */
public class ContentBusinessImpl implements ContentBusinessSrv
{

	private final Log logger = LogFactory.getLog(ContentBusinessImpl.class);

	private BehaviourFilter policyBehaviourFilter;

	private ValidationManager validationManager;
	private ContentManager contentManager;

	//--------------
	//-- public methods


	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.business.api.content.ContentBusinessSrv#addContent(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.io.File, boolean)
	 */
	public NodeRef addContent(final NodeRef parent, final String name, final File file, final boolean disableNotification)
	{
		final BusinessStackError stack = new BusinessStackError();
		// a content can be added on any kind of node (ie an attachment to a post ...)
		validationManager.validateNodeRef(parent, stack);
		validationManager.validateTitle(name, stack);
		stack.finish(logger);

		AssertUtils.canAccess(file);

		boolean wasEnabled = false;
    	try
    	{
	    	if(disableNotification)
	    	{
	    		wasEnabled = !policyBehaviourFilter.disableBehaviour(ContentNotifyAspect.ASPECT_CONTENT_NOTIFY);
	    	}

	    	return contentManager.createContent(parent, name, ContentModel.ASSOC_CONTAINS, ContentModel.TYPE_CONTENT, file, true);
    	}
		finally
        {
			if(wasEnabled)
	    	{
	    		policyBehaviourFilter.enableBehaviour(ContentNotifyAspect.ASPECT_CONTENT_NOTIFY);
	    	}
        }
	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.business.api.content.ContentBusinessSrv#addContent(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.io.File)
	 */
	public NodeRef addContent(final NodeRef parent, final String name, final File file)
	{
		return addContent(parent, name, file, false);
	}


	//--------------
	//-- private helpers


	//--------------
	//-- IOC


	/**
	 * @param policyBehaviourFilter the policyBehaviourFilter to set
	 */
	public final void setPolicyBehaviourFilter(BehaviourFilter policyBehaviourFilter)
	{
		this.policyBehaviourFilter = policyBehaviourFilter;
	}

	/**
	 * @param validationManager the validationManager to set
	 */
	public final void setValidationManager(ValidationManager validationManager)
	{
		this.validationManager = validationManager;
	}

	/**
	 * @param contentManager the contentManager to set
	 */
	public final void setContentManager(ContentManager contentManager)
	{
		this.contentManager = contentManager;
	}




}
