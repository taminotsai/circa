package eu.cec.digit.circabc.repo.external.repositories;

/**
 * Returns a proxy ticket to authenticate against ECAS.
 * 
 * @author schwerr
 */
public interface ProxyTicketResolver {
    
	/**
     * Default playground ticket, all requests with this ticket are recognized 
     * by HRS and transparently routed in the ESB to the non-secure 
     * (=playground) environment
     */
    public static final String PLAYGROUND_TICKET = "T1ck3t";
    
	/**
	 * Resolves the proxy ticket for this environment.
	 * 
	 * @return
	 */
	public String getProxyTicket();
}
