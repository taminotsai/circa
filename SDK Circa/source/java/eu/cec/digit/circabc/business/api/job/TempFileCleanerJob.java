/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.job;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.transaction.TransactionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.Assert;

import eu.cec.digit.circabc.business.helper.TemporaryFileManager;

/**
 * Job that check if new users are to be imported into circabc
 *
 * @author Yanick Pignot
 */
public class TempFileCleanerJob implements Job
{
	private static Log logger = LogFactory.getLog(TempFileCleanerJob.class);

	private TemporaryFileManager temporaryFileManager;
	private TransactionService transactionService;
	public void execute(final JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("Start job .... ");
			}

			final RetryingTransactionHelper helper = getTransactionService(context).getRetryingTransactionHelper();
			helper.doInTransaction(new RetryingTransactionCallback<Object>()
					{

						public Object execute() throws Throwable
						{

							try
							{
								AuthenticationUtil.setRunAsUserSystem();
								getTemporaryFileManager(context).removeTempFiles();
							}
							finally
							{
								AuthenticationUtil.clearCurrentSecurityContext();
							}
							return null;
						}
					}
					, false, true);


		}
		catch (final Throwable e)
		{
			logger.error("Can not run job TempFileCleanerJob", e);
		}
	}


	private synchronized void initialize(JobExecutionContext context)
	{
		final JobDataMap jobData = context.getJobDetail().getJobDataMap();

		temporaryFileManager = (TemporaryFileManager) jobData.get("temporaryFileManager");
		transactionService  = (TransactionService) jobData.get("transactionService");

		Assert.notNull(temporaryFileManager);
		Assert.notNull(transactionService);
	}

	private final TemporaryFileManager getTemporaryFileManager(final JobExecutionContext context)
	{
		if(this.temporaryFileManager == null)
		{
			initialize(context);
		}

		return this.temporaryFileManager;
	}

	private final TransactionService getTransactionService(final JobExecutionContext context)
	{
		if(this.transactionService == null)
		{
			initialize(context);
		}

		return this.transactionService;
	}
}
