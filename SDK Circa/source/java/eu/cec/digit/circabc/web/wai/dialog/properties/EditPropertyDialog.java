package eu.cec.digit.circabc.web.wai.dialog.properties;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.web.wai.wizard.property.DefineNewPropertyWizard;
import eu.cec.digit.circabc.web.wai.wizard.property.DynPropertyWrapper;



/**
 *	Bean that backs the "Edit Property" WAI Dialog.
 *
 * @author Slobodan Filipovic
 */
public class EditPropertyDialog extends DefineNewPropertyWizard {

	/** */
	private static final long serialVersionUID = -3366182849266786067L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "EditPropertyDialog";


	private static final Log logger = LogFactory.getLog(EditPropertyDialog.class);

	private DynamicProperty propertyToEdit = null;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			final String propertyAsString = parameters.get(ManagePropertiesDialog.PROPERTY_PARAMETER );
			propertyToEdit = null;

			if(propertyAsString == null)
			{
				throw new IllegalArgumentException("Impossible to edit the property if the property parameters is not seted: " + ManagePropertiesDialog.PROPERTY_PARAMETER);
			}

			propertyToEdit = this.getPropertiesService().getDynamicPropertyByID(new NodeRef(propertyAsString));

			propertyTranslations = new ArrayList<DynPropertyWrapper>(30);

			for(Map.Entry<Locale, String> entry : propertyToEdit.getLabel().entrySet())
			{
				propertyTranslations.add(new DynPropertyWrapper(entry.getValue(), entry.getKey()));
			}
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		final MLText labels = new MLText();

        for(final DynPropertyWrapper wrapper : propertyTranslations)
        {
        	labels.addValue(wrapper.getLocale(), wrapper.getValue());
        }

        String info =MessageFormat.format("the property {0} has been edited - description {1}", new Object[]{propertyToEdit.getName(),labels.toString()});
		logRecord.setInfo(info );

		this.getPropertiesService().updateDynamicPropertyLabel(propertyToEdit, labels);

		if(logger.isDebugEnabled())
		{
			logger.debug("Property " + propertyToEdit.getName() + " successfully updated under the interest group " + getActionNode().getName() +  ". New value: " + propertyToEdit.toString());
		}

		return outcome;
	}

	@Override
	public boolean getFinishButtonDisabled()
	{
		return false;
	}

	public String getPropertyName()
	{
		return this.propertyToEdit.getName();
	}

	public String getBrowserTitle()
	{
		return translate("edit_property_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_property_dialog_icon_tooltip");
	}


}
