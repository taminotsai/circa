package eu.cec.digit.circabc.service.dynamic.property;

import java.util.List;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;

public interface DynamicProperty extends Comparable<DynamicProperty> {

	//get the index of the property
	public Long getIndex();
	public MLText getLabel();
	// DateField , TextField, TextArea Selection
	public DynamicPropertyType getType();
	// Valid values for  Selection type (similar like java enum)
	public String getValidValues();
	public String getDisplayValidValues();
	public List<String> getListOfValidValues();
	public boolean isSelectionType();
	
	/**
	 * @return the node reference where the dynamic property is stored
	 */
    public NodeRef getId();
    public String getName();
    public String getLanguages();

}
