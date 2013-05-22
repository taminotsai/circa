/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package  eu.cec.digit.circabc.service.user;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import eu.cec.digit.circabc.service.CircabcServiceRegistry;
import eu.cec.digit.circabc.service.lock.LockService;

/**
 * @author Slobodan Filipovic
 *
 */
public class LdapNullUsersJob implements Job
{
	private static boolean isInitialized = false;


	private static CircabcServiceRegistry circabcServiceRegistry;

	private static Log logger = LogFactory.getLog(LdapNullUsersJob.class);


	private static UserService userService;


	private static LockService lockService;

	private static final String LDAP_NULL_USERS_JOB = "LdapNullUsersJob";

	private static final String ADMIN_USER = "admin";


	public void execute(final JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			initialize(context);
			AuthenticationUtil.setRunAsUser(ADMIN_USER);
			if ( ! lockService.isLocked(LDAP_NULL_USERS_JOB))
			{
				lockService.lock(LDAP_NULL_USERS_JOB);
				userService.updateMissingLastNamePersons();
			}
		}
		catch (final Exception e)
		{
			logger.error("Can not run job LdapNullUsersJob", e);
		}
		finally
		{
			AuthenticationUtil.clearCurrentSecurityContext();
			lockService.unlock(LDAP_NULL_USERS_JOB);
		}


		if(logger.isDebugEnabled())
		{
			logger.debug("finished...");
		}
	}



	private void initialize(final JobExecutionContext context)
	{
		if (!isInitialized)
		{

			try
			{
				AuthenticationUtil.setRunAsUser(ADMIN_USER);
				final JobDataMap jobData = context.getJobDetail().getJobDataMap();
				final Object circabcServiceRegistryObj = jobData.get("circabcServiceRegistry");
				circabcServiceRegistry = (CircabcServiceRegistry) circabcServiceRegistryObj;
				userService = circabcServiceRegistry.getUserService();
				lockService = circabcServiceRegistry.getLockService();
				isInitialized = true;
			} finally
			{
				AuthenticationUtil.clearCurrentSecurityContext();
			}
		}
	}



}
