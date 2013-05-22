/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/

package eu.cec.digit.circabc.web.ui.repo.tag.debug;

import org.alfresco.web.ui.common.tag.debug.BaseDebugTag;

/**
 * Tag implementation used to place the Circabc specific Scheduler information component on a page.
 *
 * @author Yanick Pignot
 */
public class SchedulerReporterTag extends BaseDebugTag
{
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	 public String getComponentType()
	 {
	      return "eu.cec.digit.circabc.faces.debug.SchedulerReporter";
	 }
}
