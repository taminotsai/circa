/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.wizard.struct;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.web.app.context.UIContextService;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.config.CircabcConfiguration;
import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.service.profile.ProfileException;
import eu.cec.digit.circabc.service.struct.ManagementService;

public class CreateSurveySpaceDialog extends CreateCircabcNodesWizard
{

	private static final long serialVersionUID = -4260391916230284941L;

	/** The logger */
	private static final Log logger = LogFactory.getLog(CreateSurveySpaceDialog.class);

	/**
	 * Initialises the wizard
	 */
	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

		// set the default values
		setName(CircabcConfiguration.getProperty(CircabcConfiguration.SURVEY_NODE_NAME_PROPERTIES));
		setTitle(CircabcConfiguration.getProperty(CircabcConfiguration.SURVEY_NODE_TITLE_PROPERTIES));
		setDescription(CircabcConfiguration.getProperty(CircabcConfiguration.SURVEY_NODE_DESCRIPTION_PROPERTIES));
		setIcon(ManagementService.DEFAULT_SURVEY_ICON_NAME);
	}


	/**
	 * @see org.alfresco.web.bean.wizard.AbstractWizardBean#finish()
	 */
	@Override
	protected String finishImpl(final FacesContext context, final String outcome) throws Exception {
		final RetryingTransactionHelper txnHelper = Repository.getRetryingTransactionHelper(context);
		final RetryingTransactionCallback<String> callback = new RetryingTransactionCallback<String>()
	    {
	        public String execute() throws Throwable
	        {
	            try
	            {
	            	if(getActionNode().hasAspect(CircabcModel.ASPECT_IGROOT) == false)
	        		{
	        			logger.error("A survey space try to be created under an other kind of node that an interest group. " + getActionNode());

	        			throw new IllegalArgumentException("We only can create survey under an interest group root folder.");
	        		}

	        		createSurvey(getActionNode().getNodeRef());

	        		if(logger.isDebugEnabled())
	        		{
	        			// debug perfomred by the createSurvey method.
	        		}

	                UIContextService.getInstance(context).notifyBeans();

	                if(logger.isDebugEnabled()) {
	                	logger.debug("outcome=" + outcome);
	                }
	                return outcome;
	            }
	            catch(final ProfileException pe)
	            {
	            	if(logger.isErrorEnabled()) {
	                	logger.error("Profile:" + pe.getProfileName() + " Explanation:" + pe.getExplanation(), pe);
	                }
	            	Utils.addErrorMessage(translate(MESSAGE_ID_INVITATION_ERROR, pe.getExplanation()), pe);
	                return null;
	            }
	            catch (final Throwable e)
	            {
	            	if(logger.isErrorEnabled()) {
	                	logger.error("Invitation error", e);
	                }
	                Utils.addErrorMessage(translate(MESSAGE_ID_INVITATION_ERROR, e.getMessage()), e);
	                return null;
	            }
	        }

	    };

	    return txnHelper.doInTransaction(callback, false);
	}

	@Override
	public String getPageIconAltText()
	{
		return translate("create_survey_space_icon_tooltip");
	}

	@Override
	public String getBrowserTitle()
	{
		return translate("create_survey_space_browser_title");
	}

	@Override
	public boolean isPropertiesReadOnly()
	{
		return true;
	}

	@Override
	public boolean getFinishButtonDisabled()
	{
		return false;
	}



}