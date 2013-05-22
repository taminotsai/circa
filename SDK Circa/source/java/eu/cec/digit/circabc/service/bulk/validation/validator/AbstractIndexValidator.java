package eu.cec.digit.circabc.service.bulk.validation.validator;

import org.alfresco.service.ServiceRegistry;

public abstract class AbstractIndexValidator implements IndexValidator {
	protected ServiceRegistry serviceRegistry;
	private AbstractIndexValidator() {};
	public AbstractIndexValidator(final ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
}
