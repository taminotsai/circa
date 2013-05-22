package eu.cec.digit.circabc.repo.web.scripts.bean;

import eu.cec.digit.ecas.client.validation.AuthenticationSuccess;

public interface TicketValidator
{

	/**
	 * @param ticket
	 */
	public abstract AuthenticationSuccess validateTicket(String ticket);
}