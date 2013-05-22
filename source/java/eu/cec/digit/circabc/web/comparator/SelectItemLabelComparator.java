package eu.cec.digit.circabc.web.comparator;

import java.util.Comparator;

import javax.faces.model.SelectItem;

public class SelectItemLabelComparator implements Comparator<SelectItem> {
	
	@Override
	public int compare(SelectItem selectItem1, SelectItem selectItem2)  {
	
		if  ((selectItem1.getLabel() != null) && selectItem2.getLabel() != null )
		{
			return selectItem1.getLabel().compareTo(selectItem2.getLabel()) ;
		}
		else if  ((selectItem1.getLabel() == null) && selectItem2.getLabel() == null )
		{
			return 0;
		}
		else if  ((selectItem1.getLabel() == null)  )
		{
			return -1; 
		}
		else 
		{
			return  1;
		}
			
	}


}
