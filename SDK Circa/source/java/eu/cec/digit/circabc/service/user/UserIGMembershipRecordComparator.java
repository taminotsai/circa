package eu.cec.digit.circabc.service.user;

import java.io.Serializable;
import java.util.Comparator;

public class UserIGMembershipRecordComparator implements Comparator<UserIGMembershipRecord> , Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330903237674384518L;
	
	private UserIGMembershipRecordComparator()
	{
		
	}
	
	private static class UserIGMembershipRecordComparatorHolder
	{
		public static final UserIGMembershipRecordComparator INSTANCE = new UserIGMembershipRecordComparator();
	}

	public int compare(UserIGMembershipRecord first , UserIGMembershipRecord second)
	{
		final String firstString  = first.getCategoryTitle() + first.getInterestGroupTitle() ; 
		final String secondString  = second.getCategoryTitle() + second.getInterestGroupTitle() ;
		return firstString.compareToIgnoreCase(secondString);
		
	}

	public static Comparator<UserIGMembershipRecord> getInstance()
	{
		return UserIGMembershipRecordComparatorHolder.INSTANCE;
	}
	
}
