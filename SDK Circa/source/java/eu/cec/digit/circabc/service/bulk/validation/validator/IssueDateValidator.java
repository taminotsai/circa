package eu.cec.digit.circabc.service.bulk.validation.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.springframework.extensions.surf.util.I18NUtil;

import eu.cec.digit.circabc.service.bulk.BulkService;
import eu.cec.digit.circabc.service.bulk.indexes.IndexRecord;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessage;
import eu.cec.digit.circabc.service.bulk.indexes.message.ValidationMessageImpl;
import eu.cec.digit.circabc.service.bulk.validation.ErrorType;

public class IssueDateValidator extends AbstractIndexValidator {
	private static final ThreadLocal<DateFormat> sdf = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(BulkService.INDEX_DATE_FORMAT);
		}
	};
	private static final String ERROR_DESCRIPTION = "bulk_upload_issue_date_error";

	public IssueDateValidator(final ServiceRegistry serviceRegistry) {
		super(serviceRegistry);
	}

	public void validate(final IndexRecord indexRecord,
			final List<ValidationMessage> messages) {

		if(indexRecord.getIssueDate() != null && indexRecord.getIssueDate().length() > 0) {
			try {
				@SuppressWarnings("unused")
				final Date parsed = sdf.get().parse(indexRecord.getIssueDate());
			} catch (final ParseException pe) {
				final String errorDescription = I18NUtil.getMessage(ERROR_DESCRIPTION);
				final ValidationMessage validationMessage = new ValidationMessageImpl(
					indexRecord.getRowNumber(), indexRecord.getName(), errorDescription, ErrorType.Warning);
				messages.add(validationMessage);
			}
		}
	}
}
