package eu.cec.digit.circabc.springframework.jndi;

public class JndiObjectFactoryBean extends org.springframework.jndi.JndiObjectFactoryBean {
	private String defaultAutoCommit;

	public final String getDefaultAutoCommit() {
		return defaultAutoCommit;
	}

	public final void setDefaultAutoCommit(final String defaultAutoCommit) {
		this.defaultAutoCommit = defaultAutoCommit;
	}
}
