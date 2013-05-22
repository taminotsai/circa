package eu.cec.digit.circabc.web.wai.dialog.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;



/**
 *	Bean that backs the "Edit Selection Property" WAI Dialog.
 *
 * @author Slobodan Filipovic
 */
public class EditSelectionPropertyDialog extends BaseWaiDialog{

	private static final String UPDATE_TEXT = "update-text";

	private static final String ADD_TEXT = "add-text";

	private static final String LIST_OF_VALID_VALUES = "list-of-valid-values";

	/** */
	private static final long serialVersionUID = -3366182849266786067L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "EditPropertyDialog";


	private static final Log logger = LogFactory.getLog(EditSelectionPropertyDialog.class);

	private DynamicProperty propertyToEdit = null;

	private DynamicPropertyService propertiesService;

	private String validValues;
	
	private List<String>  oldListOfValidValues;
	private List<String>  listOfValidValues;
	
	private boolean updateExistingProperties;
	private Set<String> deletedValues;
	private Map<String, String> updatedValues;
	private String addText;
	private String updateText;
	private String currentValue;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			deletedValues = new HashSet<String>();
			updatedValues = new HashMap<String,String>();
			updateExistingProperties = true;
			
			final String propertyAsString = parameters.get(ManagePropertiesDialog.PROPERTY_PARAMETER );
			propertyToEdit = null;

			if(propertyAsString == null)
			{
				throw new IllegalArgumentException("Impossible to edit the property if the property parameters is not seted: " + ManagePropertiesDialog.PROPERTY_PARAMETER);
			}

			propertyToEdit = this.getPropertiesService().getDynamicPropertyByID(new NodeRef(propertyAsString));
			this.listOfValidValues =  propertyToEdit.getListOfValidValues();
			this.oldListOfValidValues =  propertyToEdit.getListOfValidValues();
			if (this.listOfValidValues.size()>0)
			{
				currentValue = (String) this.listOfValidValues.get(0);
			}
			
			addText = "";
			updateText = "";

		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		validValues = calculateValidValues();
		this.getPropertiesService().updateDynamicPropertyValidValues(propertyToEdit, validValues, updateExistingProperties, deletedValues, updatedValues);
		return outcome;
	}

	private String calculateValidValues()
	{
		final StringBuilder result = new StringBuilder("");
		for (String item : listOfValidValues)
		{
			result
			.append(item)
			.append(DynamicPropertyService.MULTI_VALUES_SEPARATOR);
			
		}
		return result.toString();
	}

	public String getPropertyName()
	{
		return this.propertyToEdit.getName();
	}

	public String getBrowserTitle()
	{
		return translate("edit_selection_list_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("edit_selection_list_dialog_icon_tooltip");
	}

	/**
	 * @return the propertiesService
	 */
	protected final DynamicPropertyService getPropertiesService()
	{
		if (propertiesService == null)
		{
			propertiesService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getDynamicPropertieService();
		}
		return propertiesService;
	}

	/**
	 * @param propertiesService the propertiesService to set
	 */
	public final void setDynamicPropertyService(DynamicPropertyService dynamicPropertyService)
	{
		this.propertiesService = dynamicPropertyService;
	}

	

	public List<SelectItem> getListOfValidValues()
	{
		List<SelectItem> items = new ArrayList<SelectItem>() ;
		for (String  item : listOfValidValues)
		{
			items.add(new SelectItem(item));
		}
		return items;
	}

	public void setCurrentValue(String currentValue)
	{
		this.currentValue = currentValue;
	}

	public String getCurrentValue()
	{
		return currentValue;
	}

	
	
	
	public void select(final ActionEvent event)
    {
		final String selectValue = getUISelectOneValue(event, LIST_OF_VALID_VALUES);
		addText = selectValue;
		updateText = selectValue;
    }

	public void add(ActionEvent event)
	{
		final String inputValue = getUIInputValue(event, ADD_TEXT);
		if (!listOfValidValues.contains(inputValue))
		{
			listOfValidValues.add(inputValue);
		}
		
		
	}


	private String getUIInputValue(ActionEvent event, String id)
	{
		final UIInput input = (UIInput) event.getComponent().findComponent(id);
		final String inputValue = ((String) input.getValue()).trim();
		return inputValue;
	}
	
	public void update(ActionEvent event)
	{
		final String oldValue = getUISelectOneValue(event, LIST_OF_VALID_VALUES);
		final String newValue =  getUIInputValue(event, UPDATE_TEXT);
		
		final int index = listOfValidValues.indexOf(oldValue); 
		if (index >= 0 )
		{
			listOfValidValues.set(index, newValue);
			if (oldListOfValidValues.contains(oldValue))
			{
				updatedValues.put(oldValue, newValue);
			}
			else if (updatedValues.containsValue(oldValue))
			{
				String originalOldValue = null;
				for (Entry<String,String> item : updatedValues.entrySet())
				{
					if (item.getValue().equals(oldValue))
					{
						originalOldValue = item.getKey(); 
					}
				}
				if (originalOldValue != null)
				{
					updatedValues.put(originalOldValue, newValue);
				}
				
			}
			
		}
		
		
		
	}
	public void remove(ActionEvent event)
	{
		
		final String selectValue = getUISelectOneValue(event, LIST_OF_VALID_VALUES);

		final int index = listOfValidValues.indexOf(selectValue); 
		if (index >= 0 )
		{
			listOfValidValues.remove(index);
			if (oldListOfValidValues.contains(selectValue))
			{
				deletedValues.add(selectValue);
			}
		}
		

	}
	public void up(ActionEvent event)
	{
		final String selectValue = getUISelectOneValue(event, LIST_OF_VALID_VALUES);
		int index = listOfValidValues.indexOf(selectValue);
		if (index > 0 )
		{
			Collections.swap(listOfValidValues, index, index-1);
		}
	}
	public void down(ActionEvent event)
	{
		final String selectValue = getUISelectOneValue(event, LIST_OF_VALID_VALUES);
		final int index = listOfValidValues.indexOf(selectValue);
		final int size = listOfValidValues.size();
		if (index < size -1 )
		{
			Collections.swap(listOfValidValues, index, index+1);
		}
	}

	private String getUISelectOneValue(ActionEvent event, String id)
	{
		final UISelectOne select = (UISelectOne) event.getComponent().findComponent(id);
		final String selectValue = (String) select.getValue();
		return selectValue;
	}

	public void setAddText(String addText)
	{
		this.addText = addText;
	}

	public String getAddText()
	{
		return addText;
	}

	public void setUpdateText(String updateText)
	{
		this.updateText = updateText;
	}

	public String getUpdateText()
	{
		return updateText;
	}

	public void setUpdateExistingProperties(boolean updateExistingProperties)
	{
		this.updateExistingProperties = updateExistingProperties;
	}

	public boolean isUpdateExistingProperties()
	{
		return updateExistingProperties;
	}

}
