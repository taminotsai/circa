package eu.cec.digit.circabc.repo.external.repositories;

/**
 * Returns just the playground proxy ticket for test.
 * 
 * @author schwerr
 */
public class PlaygroundProxyTicketResolver implements ProxyTicketResolver {
	
	/**
	 * @see eu.cec.digit.circabc.repo.external.repositories.ProxyTicketResolver#getProxyTicket()
	 */
	@Override
	public String getProxyTicket() {
		return ProxyTicketResolver.PLAYGROUND_TICKET;
	}
}
