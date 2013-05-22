/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.admin.debug;

/**
 * Base interface to compute the report of Cron job.
 *
 * <blockquote>
 * 		 Conveys the detail properties of a given Job instance.
 *		 Jobs have a name and group associated with them, which should uniquely identify them within a single Scheduler.
 *		 Triggers are the 'mechanism' by which Jobs are scheduled. Many Triggers can point to the same Job, but a single Trigger can only point to one Job.
 * </blockquote>
 *
 * @see org.quartz.JobDetail
 *
 * @author Yanick Pignot
 */
public interface JobReport
{
	/**
	 * The Full name of a job is the result of the concatenation of its group and its name.
	 *
	 * @return the full name of the job.
	 */
	String getFullname();

	/**
	 * Since the most of jobs are in located in the group called <i><b>DEFAULT</b></i>, this method return a user friendly
	 * unique name whitout the group if this last is named <i>DEFAULT</i>
	 *
	 * @return
	 */
	String getSimpleName();

	/**
	 * @return the description of the job (if setted)
	 */
	String getDescrition();

	/**
	 * @return the class instance where the job is defined
	 */
	String getJobClass();

	/**
	 * Whether or not the Job implements the interface StatefulJob
	 *
	 * @return if the job is statefull
	 */
	boolean isStatefull();

}
