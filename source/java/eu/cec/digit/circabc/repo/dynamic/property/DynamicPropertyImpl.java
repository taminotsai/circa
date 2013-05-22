package eu.cec.digit.circabc.repo.dynamic.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyType;

public class DynamicPropertyImpl implements DynamicProperty, Serializable  {

	/** */
	private static final long serialVersionUID = -4539369354206620896L;
	private Long index;
	private String validValues ;
	private MLText label ;
	private DynamicPropertyType type;
	/** The id of the dynamic property */
    private NodeRef id;

    private DynamicPropertyImpl()
    {
    	// TODO add code
    }

    public DynamicPropertyImpl(final MLText label, final DynamicPropertyType type, final String validValues )
    {
    	this(null, label, type, validValues);
	}

    public DynamicPropertyImpl(final Long index, final MLText label, final DynamicPropertyType type, final String validValues )
    {
    	this(index, null, label, type, validValues);
	}

    /*package */ DynamicPropertyImpl(final Long index, final NodeRef id, final MLText label, final DynamicPropertyType type, final String validValues )
    {
		this.id= id;
		this.label = label;
		this.type = type;
		this.validValues = validValues;
		this.index = index;
	}


	/**
	 * @param validValues the validValues to set
	 */
	public void setValidValues(String validValues) {
		this.validValues = validValues;
	}

	/**
	 * @return the validValues
	 */
	public String getValidValues() {
		return validValues;
	}

	public String getDisplayValidValues()
	{
		return getValidValues() == null ? "" : getValidValues().replace(String.valueOf(DynamicPropertyService.MULTI_VALUES_SEPARATOR), "; ");
	}


	/**
	 * @return the label
	 */
	public MLText getLabel() {
		return label;
	}

	/**
	 * @return the index
	 */
	public Long getIndex()
	{
		return index;
	}


	/**
	 * @return the type
	 */
	public DynamicPropertyType getType() {
		return type;
	}

	public NodeRef getId() {
		return id;
	}


	public String getLanguages() {
		String result = "[]";
		if (this.label != null)
		{
			result = Arrays.toString(this.label.getLocales().toArray());
		}
		return result;
	}

	public String getName() {
		return this.label == null ? "" : this.label.getDefaultValue();
	}


	@Override
	public String toString()
	{
		return "Dynamic property "
			+ ((id != null) ? (" - id: " + id) : "")
			+ ((index != null) ? (" - index: " + index) : "")
			+ " - label: " + label
			+ " - type: " + type
			+ ((DynamicPropertyType.SELECTION.equals(type)) ? (" - validValues: " + validValues) : "");
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((index == null) ? 0 : index.hashCode());
		result = PRIME * result + ((label == null) ? 0 : label.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		result = PRIME * result + ((validValues == null) ? 0 : validValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DynamicPropertyImpl other = (DynamicPropertyImpl) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (index == null)
		{
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (validValues == null)
		{
			if (other.validValues != null)
				return false;
		} else if (!validValues.equals(other.validValues))
			return false;
		return true;
	}

	public List<String> getListOfValidValues()
	{
		final String values = this.getValidValues();

		List<String> items = null;

		if(values != null && values.length() > 0)
		{
			final StringTokenizer tokens = new StringTokenizer(values, String.valueOf(DynamicPropertyService.MULTI_VALUES_SEPARATOR), false);

			items = new ArrayList<String>(tokens.countTokens());

			while (tokens.hasMoreTokens())
			{
				items.add(tokens.nextToken());
			}
		}
		else
		{
			items = Collections.<String> emptyList();
		}

		return items;
	}

	public boolean isSelectionType()
	{
		
		return this.type.equals(DynamicPropertyType.SELECTION);
	}

	public int compareTo(DynamicProperty o)
	{
		return (int) (this.getIndex() - o.getIndex()) ;
	}

	




}
