/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.aop;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.nav.NavigationBusinessSrv;
import eu.cec.digit.circabc.service.mail.MailService;

/**
 * @author Yanick Pignot
 */
public class GenericExceptionHandler implements ExceptionHandler
{
	private final Log logger = LogFactory.getLog(GenericExceptionHandler.class);
	
	private enum MailDestinator
	{
		SUPPORT_TEAM,
		DEV_TEAM,
		SUPPORT_DEV_TEAM,
		NOBODY
	};

	private ExceptionTranslator exceptionTranslator;
	private MailService mailService;
	private NavigationBusinessSrv navigationBusinessSrv;
	
	private String message;
	private boolean printStack;
	private boolean appendCause;
	private List<String> managedClasses;
	private MailDestinator mailDestinator;

	public void init()
	{
		for(final String className: getManagedClasses())
		{
			try
			{
				exceptionTranslator.registerHandler(this, Class.forName(className));
			}
			catch (ClassNotFoundException e)
			{
				if(logger.isErrorEnabled())
				{
					logger.error("Impossible to register hndler for class " + className + " since it doens't exists");
				}
			}

		}
	}

	public String getMessageKey(Throwable error)
	{
		String msg = getMessage();
		if(msg == null)
		{
			msg = error.getMessage();
		}
		return msg;
	}

	public Object[] getMessageParameters(final Throwable error)
	{
		if(isAppendCause())
		{
			return new Object[] {mailService.getHelpdeskAddress(), ExceptionHelper.getFriendlyCause(error)};
		}
		else
		{
			return new Object[] {mailService.getHelpdeskAddress()};
		}
	}

	public void onThrows(final MethodInvocation methodInvocation, final Throwable error)
	{
		final String loggerMessage = ExceptionHelper.getFullLoggerText(error, methodInvocation, navigationBusinessSrv);	
		
		if(isPrintStack())
		{
			logger.error(loggerMessage, error);
		}
		else
		{
			logger.debug(loggerMessage, error);
		}

		if(mailDestinator != null && MailDestinator.NOBODY.equals(mailDestinator) == false)
		{
			final List<String> to = new ArrayList<String>();

			switch (mailDestinator)
			{
				case DEV_TEAM:
					to.add(mailService.getDevTeamEmailAddress());
					break;
				case SUPPORT_TEAM:
					to.add(mailService.getSupportEmailAddress());
					break;
				case SUPPORT_DEV_TEAM:
					to.add(mailService.getDevTeamEmailAddress());
					to.add(mailService.getSupportEmailAddress());
					break;
				default:
					break;
			}

			if(to.size() > 0)
			{
				try
				{
					mailService.send(mailService.getNoReplyEmailAddress(),
							to, null, 
							"[CIRCABC] crash report",
							loggerMessage 
								+ "\n\n_______________________\n\n"
								+ ExceptionHelper.exceptionAsString(error),
							false);
				}
				catch (final Throwable t)
				{
					if(logger.isWarnEnabled())
					{
						logger.warn("Impossible to send crash report to " + to.toString() + ". The mail server is probably down.", t);
					}
				}
			}
		}
	}


	// ----------
	// -- private helpers
	

	// ----------
	// -- Abstract



	// ----------
	// -- IOC


	/**
	 * @param exceptionTranslator the exceptionTranslator to set
	 */
	public final void setExceptionTranslator(ExceptionTranslator exceptionTranslator)
	{
		this.exceptionTranslator = exceptionTranslator;
	}

	/**
	 * @return the message
	 */
	public final String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public final void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the printStack
	 */
	public final boolean isPrintStack()
	{
		return printStack;
	}

	/**
	 * @param printStack the printStack to set
	 */
	public final void setPrintStack(boolean printStack)
	{
		this.printStack = printStack;
	}

	/**
	 * @param managedClasses the managedClasses to set
	 */
	public final void setManagedClasses(List<String> managedClasses)
	{
		this.managedClasses = managedClasses;
	}

	/**
	 * @return the managedClasses
	 */
	public final List<String> getManagedClasses()
	{
		return managedClasses;
	}

	/**
	 * @return the appendCause
	 */
	public final boolean isAppendCause()
	{
		return appendCause;
	}

	/**
	 * @param appendCause the appendCause to set
	 */
	public final void setAppendCause(boolean appendCause)
	{
		this.appendCause = appendCause;
	}

	/**
	 * @return the mailDestinator
	 */
	public final String getMailDestinator()
	{
		return mailDestinator.toString();
	}

	/**
	 * @param mailDestinator the mailDestinator to set
	 */
	public final void setMailDestinator(String mailDestinator)
	{
		this.mailDestinator = MailDestinator.valueOf(mailDestinator);
	}

	/**
	 * @param mailService the mailService to set
	 */
	public final void setMailService(MailService mailService)
	{
		this.mailService = mailService;
	}

	/**
	 * @param navigationBusinessSrv the navigationBusinessSrv to set
	 */
	public final void setNavigationBusinessSrv(NavigationBusinessSrv navigationBusinessSrv)
	{
		this.navigationBusinessSrv = navigationBusinessSrv;
	}

}
