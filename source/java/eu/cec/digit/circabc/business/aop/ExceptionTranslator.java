/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.business.aop;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.business.api.BusinessRuntimeExpection;
import eu.cec.digit.circabc.business.api.BusinessStackError;

/**
 * @author Yanick Pignot
 *
 */
public class ExceptionTranslator implements MethodInterceptor
{
	public Map<Class, ExceptionHandler> exceptionHandlers = new HashMap<Class, ExceptionHandler>();

	private final Log logger = LogFactory.getLog(ExceptionTranslator.class);

	private final String DEFAULT_MESSAGE = "";

	public void registerHandler(final ExceptionHandler handler, Class clazz)
	{
		if(clazz == null)
		{
			logger.error("Handler not registered beacause it doens't provide managed error type: " + handler.getClass());
		}
		else
		{
			if(BusinessStackError.class.equals(clazz) == false)
			{
				exceptionHandlers.put(clazz, handler);
			}
			else if(logger.isWarnEnabled())
			{
				logger.warn("BusinessStackError error type is managed by default. ");
			}
		}
	}

	public Object invoke(MethodInvocation mi) throws Throwable
	{
		try
        {
            return mi.proceed();
        }
		catch (final BusinessStackError stack)
		{
			throw stack;
		}
		catch (final Throwable error)
        {			
        	final BusinessStackError stack;
        	final Class<? extends Throwable> errorClass = error.getClass();

			final ExceptionHandler handler = getHandler(errorClass);

        	if(handler != null)
        	{
        		stack = new BusinessStackError(new BusinessRuntimeExpection(handler.getMessageKey(error), handler.getMessageParameters(error)));

        		try
    			{
        			handler.onThrows(mi, error);	
    			}
        		catch(final Throwable t)
        		{
        			logger.error("Impossible to perform " + handler.getClass() + ".onThrows()", t);
        		}
    			
        	}
        	else
        	{
        		if(logger.isErrorEnabled())
        		{
        			logger.error("No catched / intercepted error occurs in the business layer. Please to correct or translate it! "
        					+ "\n\tClass        : " + errorClass
        					+ "\n\tMessage      : " + error.getMessage()
        					+ "\n\tCause type   : " + error.getCause() == null ? "N/A" : error.getCause().getClass()
        					+ "\n\tCause message: " + error.getCause() == null ? "N/A" : error.getCause().getMessage()
        						);
        		}

        		stack = new BusinessStackError(new BusinessRuntimeExpection(DEFAULT_MESSAGE));
        	}

        	throw stack;
        }

	}

	private ExceptionHandler getHandler(final Class<? extends Throwable> errorClass)
	{
		ExceptionHandler handler = exceptionHandlers.get(errorClass);
		if(handler == null)
		{
			for(final Class<? extends Throwable> clazz: exceptionHandlers.keySet())
			{
				if(clazz.isAssignableFrom(errorClass))
				{
					handler = exceptionHandlers.get(clazz);
					break;
				}
			}
		}
		return handler;
	}

}
