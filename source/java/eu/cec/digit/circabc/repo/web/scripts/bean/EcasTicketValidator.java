package eu.cec.digit.circabc.repo.web.scripts.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.config.CircabcConfiguration;
import eu.cec.digit.ecas.client.configuration.ChainableSpringConfiguration;
import eu.cec.digit.ecas.client.configuration.ConfigurationException;
import eu.cec.digit.ecas.client.configuration.SpringConfigurator;
import eu.cec.digit.ecas.client.validation.AuthenticationFailure;
import eu.cec.digit.ecas.client.validation.AuthenticationSuccess;
import eu.cec.digit.ecas.client.validation.EcasValidationConfigIntf;
import eu.cec.digit.ecas.client.validation.ProxyTicketValidator;
import eu.cec.digit.ecas.client.validation.ServiceTicketInfo;
import eu.cec.digit.ecas.client.validation.TicketInfo;
import eu.cec.digit.ecas.client.validation.ValidationException;
import eu.cec.digit.ecas.client.validation.ValidationResult;

public class EcasTicketValidator implements TicketValidator 
{
	/** A logger for the class */
	static final  Log logger = LogFactory.getLog(EcasTicketValidator.class);
	private ChainableSpringConfiguration ecasConfiguration;
	private String service;
	private EcasValidationConfigIntf validationConfig;
	private SpringConfigurator sc;
	
	private String webRootUrl = CircabcConfiguration.getProperty(CircabcConfiguration.WEB_ROOT_URL);

	public EcasTicketValidator()
	{
		super();
	}

	public void setService(String service)
	{
		this.service = webRootUrl +  service;
	}

	public String getService()
	{
		return service;
	}

	public void setEcasConfiguration(ChainableSpringConfiguration ecasConfiguration)
	{
		this.ecasConfiguration = ecasConfiguration;
		sc = new SpringConfigurator();
		validationConfig = null;
		try
		{
			validationConfig = sc.getEcasValidationConfig(ecasConfiguration);
		} catch (ConfigurationException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Can not get  ecas validation configuration ", e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.repo.web.scripts.bean.TicektValidator#validateProxyTicket(java.lang.String)
	 */
	public AuthenticationSuccess validateTicket(String ticket)
	{
		AuthenticationSuccess result = null;
		try
		{
			ProxyTicketValidator pvt = new ProxyTicketValidator(validationConfig);
			TicketInfo ticketInfo = new ServiceTicketInfo(ticket, service);
			final ValidationResult validate = pvt.validate(ticketInfo);
			if (validate instanceof AuthenticationSuccess)
			{
				result =  (AuthenticationSuccess) validate;
			}
			else if (validate instanceof AuthenticationFailure)
			{
				AuthenticationFailure failure =  (AuthenticationFailure) validate;
				if (logger.isErrorEnabled())
				{
					logger.error("Error checking validation ticket " + ticket + "error mesage:" +failure.getErrorMessage()  );
				}
				result = null ;
			}
				
			
		} catch (ValidationException e)
		{
			if (logger.isErrorEnabled())
			{
				logger.error("Error checking validation ticket " + ticket , e);
			}
			return result;
		}
		catch (Exception  e)
		{
			logger.error("Error checking validation ticket " + ticket , e);
			return result;
		}
		return result;
	}

}