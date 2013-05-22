/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;

import java.text.MessageFormat;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.alfresco.web.app.Application;

import eu.cec.digit.circabc.service.newsgroup.AbuseReport;

/**
 * Convertor for an abuse
 *
 * @author yanick pignot
 */
public class AbuseConverter implements Converter
{

	private static final String MSG_REPORT = "post_moderation_abuse_html_detail";

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.AbuseConverter";

	public Object getAsObject(final FacesContext context, final UIComponent comp, final String value) throws ConverterException
	{
		return value;
	}

	public String getAsString(final FacesContext context, final UIComponent component, final Object object) throws ConverterException {

		if (object == null)
		{
			return null;
		}

		final StringBuffer buff = new StringBuffer("");

		if(object instanceof List)
		{
			for(Object obj: (List) object)
			{
				if(obj instanceof AbuseReport)
				{
					buff.append(getAsString(context, (AbuseReport) obj)).append("\n");
				}
			}
		}
		else if(object instanceof AbuseReport)
		{
			buff.append(getAsString(context, (AbuseReport) object));
		}

		return buff.toString();
	}

	public static final String getAsString(final FacesContext fc, final AbuseReport report)
	{
		return MessageFormat.format(Application.getMessage(fc, MSG_REPORT),
				report.getReporter(),
				report.getReportDate(),
				report.getMessage().replace("\n", " "));
	}

}
