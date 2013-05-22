/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.generator;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.ui.common.ComponentConstants;

/**
 * Generates a picker to add translation to a property in a TextArea instand of a simple text panel
 *
 * @author Yanick Pignot
 */
public class TranslateLongTextPropertyGeneratorNoRichText extends TranslatePropertyGenerator
{
	private static final int ROWS = 3;
	private static final int COLUMNS = 32;
	private static final String ATTRIBUTE_ROWS = "rows";
	private static final String ATTRIBUTE_COLUMNS = "cols";

	public static final  String PARAM_PROPERTY_TEXT_AREA = "textArea";
	public static final  String PARAM_PROPERTY_USE_RICH_TEXT = "useRichText";

	@SuppressWarnings("unchecked")
	@Override
	protected UIComponent buildTextField(final FacesContext context, final String id, final String propAsString)
	{
		final UIComponent component = super.buildTextField(context, id, propAsString);

		component.getAttributes().put(ATTRIBUTE_ROWS, ROWS);
		component.getAttributes().put(ATTRIBUTE_COLUMNS, COLUMNS);
		component.setRendererType(ComponentConstants.JAVAX_FACES_TEXTAREA);

		return component;

	}

	/* (non-Javadoc)
	 * @see eu.cec.digit.circabc.web.wai.generator.BaseDialogLauncherGenerator#getActionParameters(javax.faces.context.FacesContext, java.lang.String)
	 */
	@Override
	protected List<UIParameter> getActionParameters(final FacesContext context, final String id)
	{
		final List<UIParameter> parameters = super.getActionParameters(context, id);

		//add the parameter id to the dialog
		UIParameter parmeterTextArea = (UIParameter)context.getApplication().createComponent(UIParameter.COMPONENT_TYPE);
		FacesHelper.setupComponentId(context, parmeterTextArea, id + "-id-textArea");
		parmeterTextArea.setName(PARAM_PROPERTY_TEXT_AREA);
		parmeterTextArea.setValue(Boolean.TRUE);
		
		UIParameter parmeterRichText = new UIParameter();
		parmeterRichText.setName(PARAM_PROPERTY_USE_RICH_TEXT);
		parmeterRichText.setValue(Boolean.FALSE);

		parameters.add(parmeterTextArea);
		parameters.add(parmeterRichText);

		return parameters;
	}

}
