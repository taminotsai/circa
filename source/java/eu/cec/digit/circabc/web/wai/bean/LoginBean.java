/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.bean;

import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.MutableAuthenticationService;
import org.alfresco.service.cmr.security.NoSuchPersonException;
import org.alfresco.util.ApplicationContextHelper;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.AuthenticationHelper;
import org.alfresco.web.bean.LoginOutcomeBean;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.bean.repository.User;
import org.alfresco.web.bean.users.UserPreferencesBean;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.ecas.EcasAdapterService;
import eu.cec.digit.circabc.model.UserModel;
import eu.cec.digit.circabc.service.log.LogRecord;
import eu.cec.digit.circabc.service.log.LogService;
import eu.cec.digit.circabc.service.profile.ProfileManagerServiceFactory;
import eu.cec.digit.circabc.service.struct.ManagementService;
import eu.cec.digit.circabc.service.user.UserService;
import eu.cec.digit.circabc.util.CircabcUserDataBean;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.context.UICircabcContextService;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.wai.dialog.signup.SelfRegistrationDialog;

/**
 * @author atadian
 */
public class LoginBean extends org.alfresco.web.bean.LoginBean
{
	public final static String WAI_LOGIN_PAGE = "/jsp/extension/wai/login.jsp";
	public final static String BEAN_NAME = "LoginBean";
	
	private static final String MSG_ERROR_ALREADY_REGISTRED_USER = "error_already_registred_user";
    private static final String MSG_ERROR_REGISTRATION_PARAMETERS = "error_registration_parameters";

	private static final long serialVersionUID = 3966998512111743446L;

	/** Logger for this class * */
    private static final Log logger = LogFactory.getLog(LoginBean.class);

    /** The domain where the user wants to log in * */
    protected String domain = UserModel.ALFRESCO_USER_PREFIX;

    /** I18N messages */
    private static final String MSG_ERROR_UNKNOWN_USER = "error_login_user";
    private boolean registrationProcess;
    private boolean badParameters;
    private boolean ecasActivated = false;

    /** Ecas Adapter server bean reference */
    private transient EcasAdapterService ecasAdapterService;
	private transient UserService userService;
	private transient ProfileManagerServiceFactory profileManagerServiceFactory;
	private transient ManagementService managementService;
	private transient LogService logService;

    @Override
    public String login()
    {
        String outcome = null;

        // Fall-back to alfresco domain
        this.domain = UserModel.ALFRESCO_USER_PREFIX;
        outcome = super.login();

        return outcome;
    }

    /**
     * Action that perform the activation of an account and, if all is correct, log the user
     *
     * @return
     */
    public String activateAndLoginCirca()
    {
        AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
        {
            public Object doWork()
            {
                // 	activate the user if we are in a registration process scope and the parameters are valid
                ( (MutableAuthenticationService) getAuthenticationService()).setAuthenticationEnabled(getUsername() , true);
              

                if(logger.isDebugEnabled())
                {
                	logger.warn("The user " + getUsername() + " is now correctly registred in circabc. Its account is set as active.");
                }

                return null;
            }
        }, AuthenticationUtil.getSystemUserName());

        return loginCirca();
    }

    public String loginCirca()
    {
    	final String user = super.getUsername();
    	if (user.equals(""))
    	{
    		return null;
    	}


        this.setUsername(super.getUsername() );

        @SuppressWarnings("unchecked")
        final Map<Object, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

        session.put(LoginOutcomeBean.PARAM_REDIRECT_URL, null);

        super.login();
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getMessages().hasNext())
        {
            // there is a message -> probably error;
            return null;
        }
        UICircabcContextService.getInstance(FacesContext.getCurrentInstance()).circabcEntered();

        ((CircabcBrowseBean) this.browseBean).refreshBrowsing();
        
        // set language 
        final UserPreferencesBean userPreferencesBean = getUserPreferencesBean();
        final String language = userPreferencesBean.getLanguage();
        userPreferencesBean.setLanguage(language);

        return CircabcBrowseBean.WAI_BROWSE_OUTCOME;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String logout()
    {
        final FacesContext context = FacesContext.getCurrentInstance();

        final LogRecord logRecord = new LogRecord();
        logRecord.setService("Directory");
        logRecord.setActivity("Logout");

        final NodeRef circabcNodeRef = getManagementService().getCircabcNodeRef();
		logRecord.setIgID((Long) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NODE_DBID ));
		logRecord.setIgName((String) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NAME));

        // need to capture this value before invalidating the session
        boolean externalAuth = "logout".equals(getLogoutOutcome());

        final Map session = context.getExternalContext().getSessionMap();
        final User user = (User) session.get(AuthenticationHelper.AUTHENTICATION_USER);
        if (user != null)
        {
        	logRecord.setUser(user.getUserName());
            // invalidate ticket and clear the Security context for this thread
            getAuthenticationService().invalidateTicket(user.getTicket());
            getAuthenticationService().clearCurrentSecurityContext();
        };
        // remove all objects from our session by hand
        // we do this as invalidating the Portal session would invalidate all other portlets!
        for (final Object key : session.keySet())
        {
            session.remove(key);
        }

        session.put(AuthenticationHelper.SESSION_INVALIDATED, true);

        // set language to last used
        final String language = preferences.getLanguage();
        if (language != null && language.length() != 0)
        {
            Application.setLanguage(context, language);
        }

        logRecord.setOK(true);
        getLogService().log(logRecord);

        return externalAuth ? "logoutReal" : "relogin";
    }

    /**
     * Called in a JSP displayer, he returns always true and serve to init the activation
     * with the url send at the self registration process
     **/
    public boolean getInitActivation()
    {
        // DIGIT-CIRCABC-683 Workaround to avoid to display 4 times the same message du to
        // the call of this method 4 times.
        if(FacesContext.getCurrentInstance().getMessages().hasNext())
        {
            return false;
        }

        registrationProcess = false;
        badParameters = false;

        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        final String paramUserName = (String) params.get(SelfRegistrationDialog.USERNAME_URL_PARAM);
        final String activationID  = (String) params.get(SelfRegistrationDialog.ACTIVATION_KEY_URL_PARAM);

        getAuthenticationService().authenticateAsGuest();

        // Check if the parameters are presents
        if(paramUserName != null && activationID != null)
        {
            // all self registred users are in the circa domain
            final String completeUserName = paramUserName ;
            registrationProcess = true;

            // test if the person is created
            if(getPersonService().personExists(completeUserName))
            {
                AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>()
                {
                        public Object doWork()
                        {
                            NodeRef userNodeRef = getPersonService().getPerson(completeUserName);

                            if(userNodeRef!= null && userNodeRef.getId().equals(activationID))
                            {
                                if(!getAuthenticationService().getAuthenticationEnabled(completeUserName))
                                {
                                    // the parametrs are valid and the value are valid too
                                    // and no error found...
                                }
                                else
                                {
                                    // user already registred. Just inform the user.
                                    Utils.addErrorMessage(Application.getMessage(FacesContext.getCurrentInstance(), MSG_ERROR_ALREADY_REGISTRED_USER));
                                    badParameters = true;

                                    if(logger.isDebugEnabled())
                                    {
                                    	logger.debug("A person wants to activate a second time its account. " +
                                                " Account: " + paramUserName +
                                                " Activation id: " + activationID);
                                    }
                                }
                            }
                            else
                            {
                                // the activation key doesn't correspond to the user id
                                Utils.addErrorMessage(Application.getMessage(FacesContext.getCurrentInstance(), MSG_ERROR_REGISTRATION_PARAMETERS));
                                badParameters = true;

                                if(logger.isDebugEnabled())
                                {
                                	logger.debug("A person wants to activate its aacount but the activation key is not correct. " +
                                            " Account: " + paramUserName +
                                            " Activation id: " + activationID);
                                }
                            }

                            return null;
                        }
                }, AuthenticationUtil.getSystemUserName());

            }
            else
            {
                // the person is not created
                Utils.addErrorMessage(Application.getMessage(FacesContext.getCurrentInstance(), MSG_ERROR_REGISTRATION_PARAMETERS));
                badParameters = true;
            }

            if(logger.isWarnEnabled())
            {
                logger.warn("A person wants to activate an inexisting account." +
                        " Check if the same error is not reproduced too many times." +
                        " Account: " + paramUserName +
                        " Activation id: " + activationID);
            }
        }

        return true;
    }
    
    @Override
    public String getUsername()
    {
        String userName = super.getUsername();
        if (userName != null)
        {
            final int charToSearch = Character.valueOf('@').charValue();
            if (userName.indexOf(charToSearch) > 0)
            {
                userName = userName.substring(0, userName.indexOf(charToSearch));
            }
        }
        return userName;
    }

    /**
     * @return if the login page is not in a self registration process. This method should be called ONLY after
     * the call of the isRegistrationProcessWithTestingParams to init the values.
     */
    public boolean isRegistrationProcess()
    {
        return registrationProcess;
    }

    /**
     * @return if error were found in the parameters of the registration process
     */
    public boolean isBadParameters()
    {
        return badParameters;
    }

    /**
     * Login action handler for ECAS
     *
     * @return outcome view name
     */
    @SuppressWarnings("unchecked")
    public String ecasLogin()
    {
        String outcome = null;
        final LogRecord logRecord = new LogRecord();


        logger.info("[1] Loggin using ecas  ");
        final Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        logger.info("[2] userName:" + userPrincipal);
        if (userPrincipal == null )
        {
            Utils.addErrorMessage("userPrincipal is null ");
            return outcome;
        }

        final String userPrincipalName = userPrincipal.getName();
        if (userPrincipalName == null || userPrincipalName.equals(""))
        {
            Utils.addErrorMessage("userPrincipal is null ");
            return outcome;
        }

        String userName = "";

        userName = userPrincipalName  ;

        outcome = "success";
        final FacesContext fc = FacesContext.getCurrentInstance();

        try
        {
            logRecord.setService("Directory");
            logRecord.setActivity("Login");
            logRecord.setUser(userName);
            this.setUsername(userName);

        	if (logger.isInfoEnabled())
        	{
        		logger.info("[3]Getting External context:");
        	}
            final Map session = fc.getExternalContext().getSessionMap();

            try
            {
	            AuthenticationUtil.setRunAsUserSystem();
	            if (!getPersonService().personExists(userName))
	            {
	            	final CircabcUserDataBean user = new CircabcUserDataBean();
	            	user.setUserName(userName);
	            	final CircabcUserDataBean ldapUserDetail = getUserService().getLDAPUserDataByUid(userPrincipalName);
	            	user.copyLdapProperties(ldapUserDetail);
	
	            	final NodeRef circaBC = getManagementService().getCircabcNodeRef();
	            	//final NodeRef circabcUserHome = getProfileManagerServiceFactory().getProfileManagerService(circaBC).getCircaHome(circaBC);
	            	user.setHomeSpaceNodeRef(managementService.getGuestHomeNodeRef());
	            	getUserService().createUser(user, true);
	            }
            } finally {
            	// Authenticate via the authentication service, then save the
                // details of user in an object
                // in the session - this is used by the servlet filter etc. on each
                // page to check for login
                AuthenticationUtil.setRunAsUser(userName);	
            }            

            final NodeRef circabcNodeRef = getManagementService().getCircabcNodeRef();
            logRecord.setIgID((Long) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NODE_DBID ));
     		logRecord.setIgName((String) getNodeService().getProperty(circabcNodeRef, ContentModel.PROP_NAME));


            // remove the session invalidated flag (used to remove last username
            // cookie by AuthenticationFilter)
            session.remove(AuthenticationHelper.SESSION_INVALIDATED);

            // setup User object and Home space ID
            if (logger.isInfoEnabled())
        	{
            	logger.info("[4]Ready to Create User:" + userName);
        	}
            final String ticket = getEcasAdapterService().getCurrentTicket(userName);
            if (logger.isInfoEnabled())
        	{
            	logger.info("[5]Ticket:" + ticket + " for user: " + userName);
        	}

            final NodeRef nodeRef = getPersonService().getPerson(userName);
            if (logger.isInfoEnabled())
        	{
            	logger.info("[6]nodeRef:" + nodeRef + " for user: " + userName);
        	}

            final User user = new User(userName, ticket, nodeRef);

            if (logger.isInfoEnabled())
        	{
            	logger.info("[7]User Created:" + user);
        	}
            final NodeRef homeSpaceRef = (NodeRef) getNodeService().getProperty(getPersonService().getPerson(userName), ContentModel.PROP_HOMEFOLDER);

            if (logger.isInfoEnabled())
        	{
            	logger.info("[8] HomeSpace:" + homeSpaceRef);
        	}

            // check that the home space node exists - else user cannot login
            if (getNodeService().exists(homeSpaceRef) == false)
            {
                throw new InvalidNodeRefException(homeSpaceRef);
            }
            if (logger.isInfoEnabled())
        	{
            	logger.info("[9] Setting HomeSpaceId");
        	}
            user.setHomeSpaceId(homeSpaceRef.getId());

            // put the User object in the Session - the authentication servlet
            // will then allow
            // the app to continue without redirecting to the login page
            if (logger.isInfoEnabled())
        	{
            	logger.info("[10] Setting Extra session Data");
        	}
            session.put(AuthenticationHelper.AUTHENTICATION_USER, user);
//          if a redirect URL has been provided then use that
            // this allows servlets etc. to provide a URL to return too after a successful login
            final String redirectURL = (String)session.get(LoginOutcomeBean.PARAM_REDIRECT_URL);
            if (redirectURL != null)
            {
               if (logger.isDebugEnabled())
               {
                  logger.debug("Redirect URL found: " + redirectURL);
               }

               // remove redirect URL from session
               session.remove(LoginOutcomeBean.PARAM_REDIRECT_URL);

               try
               {
                  fc.getExternalContext().redirect(redirectURL);
                  fc.responseComplete();
                  return null;

               }
               catch (final IOException ioErr)
               {
            	   if (logger.isWarnEnabled())
            	   {
            		   logger.warn("Unable to redirect to url: " + redirectURL);
            	   }
               }
            }


            logRecord.setOK(true);
            getLogService().log(logRecord);
            UICircabcContextService.getInstance(FacesContext.getCurrentInstance()).circabcEntered();
            ((CircabcBrowseBean) this.browseBean).refreshBrowsing();
            // By pass redirection after login
            return CircabcBrowseBean.WAI_BROWSE_OUTCOME;

        } catch (final AuthenticationException aerr)
        {
        	outcome = null;
            // the user probably doesn't exist
        	if (logger.isErrorEnabled())
        	{
        		logger.error("[EXC] AuthenticationException:" + aerr);
        	}
            Utils.addErrorMessage(Application.getMessage(fc, MSG_ERROR_UNKNOWN_USER));
            logRecord.setOK(true);

        } catch (final InvalidNodeRefException refErr)
        {
        	outcome = null;
        	if (logger.isErrorEnabled())
        	{
        		logger.error("[EXC] InvalidNodeRefException:" + refErr);
        	}
            Utils.addErrorMessage(MessageFormat.format(Application.getMessage(fc, Repository.ERROR_NOHOME), refErr.getNodeRef().getId()));
            logRecord.setOK(false);
        }
        catch(final NoSuchPersonException ex)
        {
        	outcome = null;
            // the user probably doesn't exist
        	if (logger.isErrorEnabled())
        	{
        		logger.error("[EXC] AuthenticationException:" + ex);
        	}
            Utils.addErrorMessage(Application.getMessage(fc, MSG_ERROR_UNKNOWN_USER));
            logRecord.setOK(false);
        }
        catch (final Exception exp)
        {
        	outcome = null;
        	if (logger.isErrorEnabled())
        	{
        		logger.error("[EXC] Exception:" + exp);
        	}
            Utils.addErrorMessage(exp.getMessage());
            logRecord.setOK(false);
            outcome = "login";
        }
        logRecord.setOK(false);
        getLogService().log(logRecord);
        return outcome;
    }

    /**
     * @return the selected domain (circa or Alfresco)
     */
    public String getDomainsel()
    {
        return this.domain;
    }

    /**
     * set the selected domain
     */
    public void setDomainsel(final String sel)
    {
        this.domain = sel;
    }

    /**
     * @param personService The personService to set.
     */
    public final void setEcasAdapterService(final EcasAdapterService ecasAdapterService)
    {
        this.ecasAdapterService = ecasAdapterService;
    }

    /**
     * @return personService The personService to set.
     */
    protected final EcasAdapterService getEcasAdapterService()
    {
    	if(ecasAdapterService == null)
		{
    		ecasAdapterService = (EcasAdapterService) ApplicationContextHelper.getApplicationContext().getBean("EcasAdapterService");
		}
		return ecasAdapterService;

    }

    /**
     * To avoid cross validation problem (need domain to validate pattern), this validation will not be done. Password will be validate on service side
     *
     * @see validatePassword() for password validation
     */
    @Override
    public void validatePassword(final FacesContext context, final UIComponent component, final Object value) throws ValidatorException
    {
        return;
    }

 	/**
	 * @return the ecasActivated
	 */
	public boolean isEcasActivated()
	{
		return ecasActivated;
	}

	/**
	 * @param ecasActivated the ecasActivated to set
	 */
	public void setEcasActivated(final boolean ecasActivated)
	{
		this.ecasActivated = ecasActivated;
	}
	/**
	 * @return the UserService
	 */
	public UserService getUserService()
	{

		if(userService == null)
		{
			userService = (UserService) Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getUserService();
		}
		return userService;
	}

	/**
	 * @param UserService the UserService to set
	 */
	public void setUserService(final UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the profileManagerServiceFactory
	 */
	public ProfileManagerServiceFactory getProfileManagerServiceFactory() {
		if(profileManagerServiceFactory == null)
		{
			profileManagerServiceFactory = (ProfileManagerServiceFactory) ApplicationContextHelper.getApplicationContext().getBean("ProfileManagerServiceFactory");
		}

		return profileManagerServiceFactory;
	}

	/**
	 * @param profileManagerServiceFactory the profileManagerServiceFactory to set
	 */
	public void setProfileManagerServiceFactory(final ProfileManagerServiceFactory profileManagerServiceFactory) {
		this.profileManagerServiceFactory = profileManagerServiceFactory;
	}

	/**
	 * @return the managementService
	 */
	protected final ManagementService getManagementService()
	{
		if(managementService == null)
		{
			managementService = (ManagementService) ApplicationContextHelper.getApplicationContext().getBean("ManagementService");
		}
		return managementService;
	}

	/**
	 * @param managementService the managementService to set
	 */
	public final void setManagementService(final ManagementService managementService) {
		this.managementService = managementService;
	}
	/**
	 * @return the logService
	 */
	protected final LogService getLogService()
	{
		if(logService == null)
		{
			logService  = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getLogService();
		}
		return logService;
	}

	/**
	 * @param logService the logService to set
	 */
	public final void setLogService(final LogService logService)
	{
		this.logService = logService;
	}

}