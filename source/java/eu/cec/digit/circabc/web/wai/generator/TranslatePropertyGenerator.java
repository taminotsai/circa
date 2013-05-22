/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.generator;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.web.app.Application;
import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.ui.common.ComponentConstants;
import org.alfresco.web.ui.repo.component.property.PropertySheetItem;
import org.alfresco.web.ui.repo.component.property.UIPropertySheet;
import org.springframework.extensions.webscripts.ui.common.ConstantMethodBinding;

import eu.cec.digit.circabc.web.ui.common.component.UIActionLink;
import eu.cec.digit.circabc.web.ui.common.component.UIPanel;
import eu.cec.digit.circabc.web.wai.dialog.ml.TranslatePropertyDialog;

/**
 * Generates a picker to add translation to a property.
 *
 * @author Yanick Pignot
 */
public class TranslatePropertyGenerator extends BaseDialogLauncherGenerator
{
	private static final String MSG_PICK_VALUE = "translate_property_action_value";
	private static final String MSG_PICK_TOOLTIP = "translate_property_action_tooltip";

    private static final String ACTION = TranslatePropertyDialog.WAI_DIALOG_CALL;

    private static final String ACTION_LINK_COMPONENT = "eu.cec.digit.circabc.faces.ActionLink";
    private static final String RENDERER_ACTIONLINK = "eu.cec.digit.circabc.faces.ActionLinkRenderer";

    private static final int SIZE = 35;
    private static final int MAX_LENGTH = 1024;
    private static final String STYLE_CLASS = "translateLink";

    private static final String ATTRIBUTE_SIZE = "size";
	private static final String ATTRIBUTE_MAX_LENGTH = "maxlength";

	@Override
	protected String getTextContent(FacesContext context, String id)
	{
		return (String) getNode().getProperties().get(id);
	}

	@Override
	protected String getAction(FacesContext context, String id)
	{
		return ACTION;
	}

	@Override
	protected String getActionTooltip(FacesContext context, String id)
	{
		return Application.getMessage(context, MSG_PICK_TOOLTIP);
	}

	@Override
	protected String getActionValue(FacesContext context, String id)
	{
		return Application.getMessage(context, MSG_PICK_VALUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected UICommand buildActionCommand(final FacesContext context, final String id)
	{
        //add it a launch dialog button
		UIActionLink picker = (UIActionLink) context.getApplication().createComponent(ACTION_LINK_COMPONENT);
		FacesHelper.setupComponentId(context, picker, id + "-launcher");
		picker.setValue(getActionValue(context, id));
		picker.setTooltip(getActionTooltip(context, id));
		picker.setAction(new ConstantMethodBinding(getAction(context, id)));
		picker.setActionListener(context.getApplication().createMethodBinding(ACTION_LISTENER, ACTION_CLASS_ARGS));
		picker.setRendererType(RENDERER_ACTIONLINK);

		picker.getAttributes().put(ATTRIBUTE_STYLE_CLASS, STYLE_CLASS);

		return picker;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected UIComponent buildTextField(final FacesContext context, final String id, final String propAsString)
	{
		// add it a text read only text field
		UIInput textField = (UIInput)context.getApplication().createComponent(
	            ComponentConstants.JAVAX_FACES_INPUT);
		textField.setRendererType(ComponentConstants.JAVAX_FACES_TEXT);

		textField.setValue(propAsString);
		FacesHelper.setupComponentId(context, textField, id);

		textField.getAttributes().put(ATTRIBUTE_SIZE, SIZE);
		textField.getAttributes().put(ATTRIBUTE_MAX_LENGTH, MAX_LENGTH);

		return textField;
	}

	 @SuppressWarnings("unchecked")
	 protected void setupProperty(FacesContext context, UIPropertySheet propertySheet,
	         PropertySheetItem item, PropertyDefinition propertyDef, UIComponent component)
	 {

		 super.setupProperty(context, propertySheet, item, propertyDef, component);

		 // create and set the value binding
	     final ValueBinding vb = component.getValueBinding("value");

	     if(vb != null && component instanceof UIPanel)
	     {
	    	 final UIPanel panel = (UIPanel) component;
	    	 for(Object children : panel.getChildren())
             {
            	if(children instanceof UIOutput )
            	{
            		((UIOutput)children).setValueBinding("value", vb);
            		break;
            	}
             }
	     }
	 }
}
