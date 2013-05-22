/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.alfresco.service.cmr.ml.ContentFilterLanguagesService;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.web.bean.repository.Node;
import org.alfresco.web.bean.users.UserPreferencesBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.service.keyword.KeywordsService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Manage keywords" WAI page.
 *
 * @author Yanick Pignot
 */
public class ManageKeywordsDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 7766640743571143699L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ManageKeywordsDialog";
	/** The constant for the 'keyword' parameter */
	public static final String KEYWORD_PARAMETER = "keyword";

	protected static final String NULL_VALUE = "null";

	/** Logger (coppepa: logger must be final) */
	private static final Log logger = LogFactory.getLog(ManageKeywordsDialog.class);

	protected List<Keyword> keywords;
	private SelectItem[] languages;

	private String selectedLanguage = null;

	private transient KeywordsService keywordsService;
	private transient ContentFilterLanguagesService contentFilterLanguagesService;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			initKeywords();
		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return null;
	}

	@Override
	public void restored()
	{
		initKeywords();
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate("close");
	}

	public String getBrowserTitle()
	{
		return translate("manage_keyword_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_keyword_dialog_icon_tooltip");
	}

	public Node getInterestGroup()
	{
		return getActionNode();
	}

   /**
    * Method calls by the dialog to getr the available langages.
    *
    * @return the list of languages where at least one keyword define
    */
   public SelectItem[] getLanguages()
   {
	   if(languages == null)
	   {
		   // get the list of filter languages
		   final List<String> languagesAsList = new ArrayList<String>(30);

		   for(final Keyword k : keywords)
		   {
			   if(k.isKeywordTranslated())
			   {
				   final MLText mlValues = k.getMLValues();
				   String lang = null;

				   for (final Map.Entry<Locale, String> entry : mlValues.entrySet())
				   {
					   lang = entry.getKey().getLanguage();
					   if(lang != null && !languagesAsList.contains(lang))
					   {
						   languagesAsList.add(lang);
					   }
				   }
			   }
		   }

		   // set the item selection list
		   SelectItem[] items = new SelectItem[languagesAsList.size() + 1];
		   int idx = 0;

		   // include the <All Languages>
		   String allLanguagesStr = translate(UserPreferencesBean.MSG_CONTENTALLLANGUAGES);
		   items[idx] = new SelectItem(NULL_VALUE, allLanguagesStr);
		   idx++;

		   for (String lang : languagesAsList)
		   {
		         String label = getContentFilterLanguagesService().getLabelByCode(lang);
		         items[idx] = new SelectItem(lang, label);
		         idx++;
		   }

		   return items;
	   }

	   return languages;

   }

    /**
	 * @return the keywords
	 */
	public List<KeywordWrapper> getKeywords()
	{
		List<KeywordWrapper> keys = null;

		if(selectedLanguage == null || selectedLanguage.equals(NULL_VALUE))
		{
			keys = new ArrayList<KeywordWrapper>(keywords.size());

			for(final Keyword k : keywords)
			{
				String value = k.getMLValues().getValues().toString();

				keys.add(new KeywordWrapper(k.getId(), value.substring(1, value.length()-1)));
			}
		}
		else
		{
			keys = new ArrayList<KeywordWrapper>(keywords.size());

			for(final Keyword k : keywords)
			{
				final MLText mlValues = k.getMLValues();
				String value = null;

				for (final Map.Entry<Locale, String> entry : mlValues.entrySet())
				{
					if(entry.getKey().getLanguage().equalsIgnoreCase(selectedLanguage))
					{
						if(value == null)
						{
							value = entry.getValue();
							break;
						}
					}
				}

				if(value != null)
				{
					keys.add(new KeywordWrapper(k.getId(), value));
				}
			}
		}
		return keys;
	}

	/**
	 * Change listener for the method select box
	 */
	public void updateList(ValueChangeEvent event)
    {
		this.selectedLanguage = (String) event.getNewValue();
    }

   protected void initKeywords()
   {
	   if(logger.isDebugEnabled())
	   {
		   logger.debug("Trying to retreive the keyword list for the interest group : " + getInterestGroup().getName() +  "(" + getInterestGroup().getNodeRef() + ")");
	   }

	   selectedLanguage = null;
	   languages = null;
	   keywords = getKeywordsService().getKeywords(getInterestGroup().getNodeRef());

	   if(logger.isDebugEnabled())
	   {
		   logger.debug("Keyword successfully retreived for the Interest Group : " + getInterestGroup().getName() +  "(" + getInterestGroup().getNodeRef() + ")"
				   + "\n    All Keywords" + keywords
				   + "\n    All languages " + Arrays.toString( getLanguages()));
	   }
   	}

   	/**
	 * @return the selectedLanguage
	 */
	public String getSelectedLanguage()
	{
		return selectedLanguage;
	}

	/**
	 * @param selectedLanguage the selectedLanguage to set
	 */
	public void setSelectedLanguage(String selectedLanguage)
	{
		this.selectedLanguage = selectedLanguage;
	}


	/**
	 * @return the keywordsService
	 */
	protected final KeywordsService getKeywordsService()
	{
		if(keywordsService == null)
		{
			keywordsService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getKeywordsService();
		}
		return keywordsService;
	}

	/**
	 * @param keywordsService the keywordsService to set
	 */
	public final void setKeywordsService(KeywordsService keywordsService)
	{
		this.keywordsService = keywordsService;
	}

	/**
	 * @return the contentFilterLanguagesService
	 */
	protected final ContentFilterLanguagesService getContentFilterLanguagesService()
	{
		if(contentFilterLanguagesService == null)
		{
			contentFilterLanguagesService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getContentFilterLanguagesService();
		}
		return contentFilterLanguagesService;
	}

	/**
	 * @param contentFilterLanguagesService the contentFilterLanguagesService to set
	 */
	public final void setContentFilterLanguagesService(ContentFilterLanguagesService contentFilterLanguagesService)
	{
		this.contentFilterLanguagesService = contentFilterLanguagesService;
	}
}
