package eu.cec.digit.circabc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConvertRowToColumn<T> {
	
	public final List<List<T>> convertToHorizontalRead(final List<T> list, final int maxColumn) 
	{		
		return splitInSubLists(list, maxColumn);
	}
	
	public final List<List<T>> convertToVerticalRead(final List<T> list, final int maxColumn) 
	{
		final List<List<T>> subList = splitInSubLists(list, maxColumn);
		final List<List<T>> subListVertical = convertRowToColumn(subList); 
		return subListVertical;
	}
	
	private List<List<T>> splitInSubLists(final List<T> list, final int maxColumn)
	{
		final List<List<T>> subLists = new ArrayList<List<T>>(maxColumn);
		final Iterator<T> listIterator = list.iterator();
		final int maxSubListSize = (list.size() / maxColumn) + 1;
		while(listIterator.hasNext()) {
			final List<T> subList = new ArrayList<T>(maxSubListSize);
			int currentElementCount = 1;
			while(listIterator.hasNext() && currentElementCount <= maxSubListSize) {
				subList.add(listIterator.next());
				currentElementCount++;
			}
			subLists.add(subList);
		}		
		return subLists;
	}
	
	private List<List<T>> convertRowToColumn(final List<List<T>> subList)
	{
		final List<List<T>> columns = new ArrayList<List<T>>();
		final int max = getMaxSize(subList);
		
		for(int i = 1; i <= max; i++) {
			columns.add(new ArrayList<T>());
		}
		
		for(final List<T> rowElement : subList) 
		{
			int columnPos = 0;
			for(final T columnElement : rowElement)
			{
				columns.get(columnPos).add(columnElement);
				columnPos++;
			}
		}
		return columns;
	}
	
	private int getMaxSize(final List<List<T>> subList)
	{
		int max = 0;
		for(final List<T> row : subList) 
		{
			if(max < row.size()) {
				max = row.size(); 
			}
		}
		return max;
	}
}
