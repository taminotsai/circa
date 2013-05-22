/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.admin.debug;

import org.quartz.JobDetail;

import eu.cec.digit.circabc.service.admin.debug.JobReport;

/**
 * Concrete impelmentation of a Job Report
 *
 * @author Yanick Pignot
 */
public class JobReportImpl implements JobReport
{

	private JobDetail jobDetail;

	/**
	 * @param jobDetal
	 */
	public JobReportImpl(JobDetail jobDetal)
	{
		super();
		this.jobDetail = jobDetal;
	}

	public String getDescrition()
	{
		return ReportUtils.getSecuredString(jobDetail.getDescription());
	}

	public String getFullname()
	{
		return jobDetail.getFullName();
	}

	public String getSimpleName()
	{
		return ReportUtils.getDisplayName(jobDetail.getGroup(), jobDetail.getName());
	}

	public String getJobClass()
	{
		return jobDetail.getJobClass().getName();
	}

	public boolean isStatefull()
	{
		return jobDetail.isStateful();
	}

}
