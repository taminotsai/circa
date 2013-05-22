package eu.cec.digit.circabc.web.comparator;

import java.util.Comparator;

import javax.faces.model.SelectItem;

public class SelectItemDescriptionComparator implements Comparator<SelectItem>{

	@Override
	public int compare(SelectItem selectItem1, SelectItem selectItem2) {
		
		if  ((selectItem1.getDescription()  != null) && selectItem2.getDescription() != null )
		{
			return selectItem1.getDescription().compareTo(selectItem2.getDescription()) ;
		}
		else if  ((selectItem1.getDescription() == null) && selectItem2.getDescription() == null )
		{
			return 0;
		}
		else if  ((selectItem1.getDescription() == null)  )
		{
			return -1; 
		}
		else 
		{
			return  1;
		}
	}

}
