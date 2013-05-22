package eu.cec.digit.circabc.service.user;

import java.io.Serializable;
import java.util.Comparator;

public class UserCategoryMembershipRecordComparator implements Comparator<UserCategoryMembershipRecord> , Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -553338713603165294L;
	private UserCategoryMembershipRecordComparator()
	{
		
	}

	private static class UserIGMembershipRecordComparatorHolder
	{
		public static final UserCategoryMembershipRecordComparator INSTANCE = new UserCategoryMembershipRecordComparator();
	}
	
	public int compare(UserCategoryMembershipRecord first, UserCategoryMembershipRecord second)
	{
		
		return first.getCategory().compareToIgnoreCase(second.getCategory()) ;
	}
	
	
	public static Comparator<UserCategoryMembershipRecord>  getInstance()
	{
		return UserIGMembershipRecordComparatorHolder.INSTANCE;
	}

}
