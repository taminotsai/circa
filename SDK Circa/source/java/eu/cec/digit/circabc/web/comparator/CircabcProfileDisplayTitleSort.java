package eu.cec.digit.circabc.web.comparator;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.wai.dialog.profile.AccessProfileWrapper;

public class CircabcProfileDisplayTitleSort implements Comparator<AccessProfileWrapper> {
	
	private static final Log logger = LogFactory.getLog(CircabcProfileDisplayTitleSort.class);
	Collator laguageCollator; 
	public CircabcProfileDisplayTitleSort(String language) {
		 try {
			 Locale locale = new Locale(language);
			 laguageCollator = Collator.getInstance(locale);
		} catch (Exception e) {
			if (logger.isErrorEnabled())
			{
				logger.error("Can not set Collator for language :" + language , e);
				logger.error("Availible locales are :" + Arrays.toString(Collator.getAvailableLocales()) );
			}
		}
	}

	@Override
	public int compare(AccessProfileWrapper profile1, AccessProfileWrapper profile2) {
		if (laguageCollator == null)
		{
			return profile1.getDisplayTitle().compareToIgnoreCase( profile2.getDisplayTitle());
		}
		else
		{
			return laguageCollator.compare(profile1.getDisplayTitle(), profile2.getDisplayTitle());
		}
		
		
	}

}
