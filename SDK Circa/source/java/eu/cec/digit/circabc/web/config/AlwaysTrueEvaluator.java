/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.config;

import org.springframework.extensions.config.evaluator.Evaluator;

/**
 * Evaluator that returns always true. Mainly used to override Alfresco property order configuration.
 *
 * @author Yanick Pignot
 */
public class AlwaysTrueEvaluator implements Evaluator
{

	public boolean applies(Object obj, String condition)
	{
		  return true;
	}

}
