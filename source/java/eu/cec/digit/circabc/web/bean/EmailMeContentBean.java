/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.bean;

import java.text.MessageFormat;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.common.component.UIActionLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.BusinessRegistry;
import eu.cec.digit.circabc.web.Services;

/**
 * Bean for handle action download content via email.
 *
 * @author patrice.coppens@trasys.lu UC 1.3.2 Download content via email
 */
public class EmailMeContentBean
{
	/** Logger for this class * */
	private static final Log LOGGER = LogFactory
			.getLog(EmailMeContentBean.class);

	private static final String MSG_SUCCESS = "mail_me_node_action_success";
	private static final String MSG_FAILURE = "mail_me_node_action_failure";

	private BusinessRegistry businessRegistry;
	private NodeService nodeService;

	/**
	 * action link mail me the content.
	 *
	 * @param event
	 *            ActionEvent
	 */
	public void mailMe(ActionEvent event)
	{
		final UIActionLink link = (UIActionLink) event.getComponent();
		final Map<String, String> params = link.getParameterMap();
		final String id = params.get("id");

		if (id == null || id.length() == 0)
		{
			return;
		}

		final FacesContext context = FacesContext.getCurrentInstance();
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
		{
			public Object execute() throws Throwable
			{
				final NodeRef ref = new NodeRef(Repository.getStoreRef(), id);

				final boolean success = getBusinessRegistry().getMailMeContentBusinessSrv().send(ref);

				if(success)
				{
					final String successMsg = Application.getMessage(context, MSG_SUCCESS);
					final String name = (String) getNodeService().getProperty(ref, ContentModel.PROP_NAME);
					Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, MessageFormat.format(successMsg, name));
				}
				else
				{
					Utils.addErrorMessage(Application.getMessage(context, MSG_FAILURE));
				}

				return null;
			}
		};

		try
		{
			txnHelper.doInTransaction(callback, true);
		}
		catch (final InvalidNodeRefException e)
		{
			final String errorMessage = Application.getMessage(context, Repository.ERROR_NODEREF);
			final String formatedMsg = MessageFormat.format(errorMessage, id);

			Utils.addErrorMessage(formatedMsg);

			if (LOGGER.isWarnEnabled())
			{
				LOGGER.warn(formatedMsg);
			}
		}
		catch (Throwable err)
		{
			Utils.addErrorMessage(Application.getMessage(context, MSG_FAILURE), err);
		}
	}

	private final BusinessRegistry getBusinessRegistry()
	{
		if(businessRegistry == null)
		{
			businessRegistry = Services.getBusinessRegistry(FacesContext.getCurrentInstance());
		}

		return businessRegistry;
	}

	private final NodeService getNodeService()
	{
		if(nodeService == null)
		{
			nodeService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getNodeService();
		}

		return nodeService;
	}

	/**
	 * @param businessRegistry the businessRegistry to set
	 */
	public final void setBusinessRegistry(BusinessRegistry businessRegistry)
	{
		this.businessRegistry = businessRegistry;
	}


	/**
	 * @param nodeService the nodeService to set
	 */
	public final void setNodeService(NodeService nodeService)
	{
		this.nodeService = nodeService;
	}
}
