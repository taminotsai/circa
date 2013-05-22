package eu.cec.digit.circabc.spring.interceptor;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class MethodCacheInterceptor implements MethodInterceptor, InitializingBean {
	private static final Log logger = LogFactory.getLog(MethodCacheInterceptor.class);

	private Cache cache;

	/**
	 * sets cache name to be used
	 */
	public void setCache(final Cache cache) {
		this.cache = cache;
	}

	/**
	 * Checks if required attributes are provided.
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache, "A cache is required. Use setCache(Cache) to provide one.");
	}

	/**
	 * main method caches method result if method is configured for caching
	 * method results must be serializable
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final String targetName = invocation.getThis().getClass().getName();
		final String methodName = invocation.getMethod().getName();
		final Object[] arguments = invocation.getArguments();
		Object result;
		final StringBuffer sb = new StringBuffer();
		final String cacheKey = getCacheKey(targetName, methodName, arguments);
		Element element = cache.get(cacheKey);
		if (logger.isTraceEnabled()) {
			final StringBuffer sbArgs = new StringBuffer();
			for(final Object argument : arguments) {
				sbArgs.append(argument).append(" ");
			}
			sb.append("calling intercepted method with params:\n" + sbArgs.toString().trim() + "\n");
		}
		if (element == null) {
			// call target/sub-interceptor
			if (logger.isTraceEnabled()) {
				sb.append(" access not succedded for cache:" + cache.getName() + "\n");
			}
			result = invocation.proceed();

			// cache method result
			element = new Element(cacheKey, (Serializable) result);
			cache.put(element);
		} else {
			if (logger.isTraceEnabled())
				sb.append(" access succedded for cache:" + cache.getName() + "\n");
		}
		if (logger.isTraceEnabled()) {
			sb.append(" return:" + element.getValue() + " for cache key:" + element.getKey() + "\n" );
			logger.trace(sb);
		}
		return element.getValue();
	}

	/**
	 * creates cache key: targetName.methodName.argument0.argument1...
	 */
	private String getCacheKey(final String targetName, final String methodName, final Object[] arguments) {
		final StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return cache.getName() + sb.toString();
	}

}
