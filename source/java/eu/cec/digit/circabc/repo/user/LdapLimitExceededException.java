package eu.cec.digit.circabc.repo.user;


public class LdapLimitExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6026579913500008873L;

	public LdapLimitExceededException(final String message) {
		super(message);
	}

	@SuppressWarnings("unused")
	public LdapLimitExceededException() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public LdapLimitExceededException(final String message, final Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public LdapLimitExceededException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
