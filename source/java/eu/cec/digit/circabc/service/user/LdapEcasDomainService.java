package eu.cec.digit.circabc.service.user;

import java.util.Map;
import java.util.Set;

/**
 * @author Slobodan Filipovic
 * 
 */
public interface LdapEcasDomainService
{
	
	
	/**
	 * Init spring bean
	 */
	public void init();
	/**
	 * @return Set of ECAS domains keys
	 * 
	 */
	public Set<String> getAllEcasDomains();
	/**
	 * @return map of ECAS domains keys and default description (english) 
	 */
	public Map<String,String> getDefaultEcasDomains();
	
	/**
	 * @param language language for example en,fr,
	 * @return
	 */
	public Map<String,String> getEcasDomains(String language);
	
}
