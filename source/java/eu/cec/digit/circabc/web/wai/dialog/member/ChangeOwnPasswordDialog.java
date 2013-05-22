/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.member;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.validator.PasswordValidator;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean to handle change of user own password
 *
 * @author Guillaume
 */
public class ChangeOwnPasswordDialog extends BaseWaiDialog
{

	private static final long serialVersionUID = -8451251711114914639L;

	public static final String BEAN_NAME = "ChangeOwnPasswordDialog";

	/** Logger */
	private static final Log logger = LogFactory.getLog(ChangeOwnPasswordDialog.class);

	/** The I18N message string for old password error */
	protected String err_old_password_incorrect = "change_own_password_error_old_password_incorrect";

	/** The I18N message string for password rules error */
	protected String err_new_password_incorrect = "change_own_password_error_rule_password_incorrect";

	/** The I18N message string for match error between twice password */
	protected String err_confirm_password_incorrect = "change_own_password_error_confirm_password_incorrect";

	/** The I18N message string for successfull password change */
	protected String msg_successfull_password_change = "change_own_password_successfull_password_change";

	/** The oldPassword */
	protected String oldPassword = "";

	/** The new password */
	protected String newPassword = "";

	/** The new password confirmation */
	protected String confirmPassword = "";

	/** The userName */
	protected String userName = null;

	/** The authentification Service reference */
	private MutableAuthenticationService authenticationService = null;

	/**
	 * Action handler called for OK button press on Change My Password screen
	 * For this screen the user is required to enter their old password - effectively login.
	 */
	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if (this.oldPassword != null && this.newPassword != null && this.confirmPassword != null)
		{
			if (this.newPassword.equals(this.confirmPassword))
			{
				// Try password rule validation
				boolean errorFound = false;
				PasswordValidator passwordValidator = new PasswordValidator();
				try
				{
					passwordValidator.evaluate(this.newPassword);
				}
				catch (ValidatorException ValidatorException)
				{
					errorFound = true;
				}

				if (errorFound)
				{
					// Password doesn't respect rules
					outcome = null;
					Utils.addErrorMessage(translate(err_new_password_incorrect));
				}
				else
				{
					// Seem all good - Old password is verify by old password
					try
					{
						getAuthenticationService().updateAuthentication(this.userName, this.oldPassword.toCharArray(), this.newPassword.toCharArray());
					}
					catch (Exception e)
					{
						outcome = null;
						if(logger.isWarnEnabled())
							logger.warn(translate(Repository.ERROR_GENERIC, e.getMessage()), e);
						Utils.addErrorMessage(translate( err_old_password_incorrect));
					}
				}
			}
			else
			{
				// Password doesn't match
				outcome = null;
				Utils.addErrorMessage(translate(err_confirm_password_incorrect));
			}
		}
		else
		{
			outcome = null;
			if(logger.isErrorEnabled())
				logger.error("finishImpl : Piracy warning from |"+ getUserName() +"| a value is null");
			Utils.addErrorMessage(translate(err_confirm_password_incorrect));
		}

		return outcome;
	}

	@Override
	protected String doPostCommitProcessing(FacesContext context, String outcome)
	{
		this.isFinished = false;
		if (outcome == null)
		{
			// Previous error -> redisplay
			return null;
		}

		Utils.addStatusMessage(FacesMessage.SEVERITY_INFO, translate(msg_successfull_password_change));

		// Reset transient value
		this.userName = null;
		this.confirmPassword = "";
		this.newPassword = "";
		this.oldPassword = "";

		return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME;
	}

	/**
	 * Getter for the oldpassword
	 *
	 * @return Value of oldpassword
	 */
	public String getOldPassword()
	{
		return this.oldPassword;
	}

	/**
	 * Setter for the old password
	 *
	 * @param oldPassword The value to set
	 */
	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	/**
	 * Getter for the new password
	 *
	 * @return Value of new password
	 */
	public String getNewPassword()
	{
		return this.newPassword;
	}

	/**
	 * Setter for the new password
	 *
	 * @param newPassword The value to set
	 */
	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	/**
	 * Getter for the confirm password
	 *
	 * @return Value of confirm password
	 */
	public String getConfirmPassword()
	{
		return this.confirmPassword;
	}

	/**
	 * Setter for the confirm password
	 *
	 * @param confirmPassword The value to set
	 */
	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	/**
	 * Getter for the user name
	 *
	 * @return Value of user name
	 */
	public String getUserName()
	{
		if (this.userName == null)
		{
			this.userName = getNavigator().getCurrentUser().getUserName();
		}
		return this.userName;
	}


	public String getBrowserTitle()
	{
		return translate("change_own_password_header");
	}

	public String getPageIconAltText()
	{
		return translate("change_own_password_icon_tooltip");
	}

	@Override
	public String getFinishButtonLabel()
	{
		return translate("change_own_password_change_button");
	}

	/**
	 * @return the authenticationService
	 */
	protected final MutableAuthenticationService getAuthenticationService()
	{
		if(authenticationService == null)
		{
			authenticationService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getAuthenticationService();
		}
		return authenticationService;
	}

	/**
	 * @param authenticationService the authenticationService to set
	 */
	public final void setAuthenticationService(MutableAuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}

}