/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.search;

import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_1;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_10;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_11;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_12;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_13;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_14;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_15;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_16;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_17;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_18;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_19;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_2;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_20;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_3;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_4;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_5;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_6;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_7;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_8;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_DYN_ATTR_9;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_EXPIRATION_DATE;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_ISSUE_DATE;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_KEYWORD;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_REFERENCE;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_SECURITY_RANKING;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_STATUS;
import static eu.cec.digit.circabc.model.DocumentModel.PROP_URL;
import static eu.cec.digit.circabc.model.DocumentModel.SECURITY_RANKINGS;
import static eu.cec.digit.circabc.model.DocumentModel.STATUS_VALUES;
import static org.alfresco.model.ContentModel.PROP_LOCALE;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.CachingDateFormat;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.bean.search.SearchContext;
import org.alfresco.web.bean.search.SearchContextDelegate;
import org.alfresco.web.bean.users.UserPreferencesBean;
import org.alfresco.web.ui.repo.component.UISearchCustomProperties;
import org.apache.lucene.queryParser.QueryParser;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.model.DocumentModel;
import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.service.keyword.Keyword;
import eu.cec.digit.circabc.service.keyword.KeywordsService;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.WebClientHelper;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcBrowseBean;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.bean.override.CircabcUserPreferencesBean;
import eu.cec.digit.circabc.web.repository.IGServicesNode;
import eu.cec.digit.circabc.web.ui.repo.converter.KeywordConverter;
import eu.cec.digit.circabc.web.wai.dialog.WaiDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Bean that back the WAI advanced search.
 *
 * @author yanick pignot
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * TODO QueryParser is taken from Lucene?
 */
public class AdvancedSearchDialog extends org.alfresco.web.bean.search.AdvancedSearchDialog implements WaiDialog
{

	   public static final String RESET = "reset";

	   private static final long serialVersionUID = -8191901120224724519L;

       public static final String BEAN_NAME = "CircabcAdvancedSearchDialog";
       public static final String DIALOG_NAME = "advancedSearchDialogWai";
       public static final String WAI_DIALOG_CALL = CircabcNavigationHandler.WAI_DIALOG_PREFIX +  DIALOG_NAME;

       protected static final String NO_SELECTION = "NONE";
       
       private static final String SAVED_SEARCHES_USER = "user";
       private static final String SAVED_SEARCHES_GLOBAL = "global";
       

       private static final String SPACE_STRORE_STRING = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE.toString();

       private boolean forceAnd = false;

       private CircabcUserPreferencesBean userPreferencesBean;
       private transient KeywordsService keywordsService;
       private transient DynamicPropertyService dynamicPropertyService;

       @Override
       public void init(Map<String, String> parameters)
       {
           super.init(parameters);

           boolean reset = true;

           if(parameters != null)
           {
        	    reset = parameters.containsKey(RESET) ? Boolean.parseBoolean(parameters.get(RESET)) : true;
           }
           else
           {
        	   reset = false;
           }

           if(reset)
           {
        	   reset(null);
        	   this.forceAnd = false;
           }
           
           // Sets the initial location to know where to save the search
           // location path search
           NodeRef location = null;
           
           if (properties.getLookin().equals(SearchProperties.LOOKIN_INTEREST_GROUP))
           {
               location = ((CircabcNavigationBean) navigator).getCurrentIGRoot().getNodeRef();
           }
           else if (properties.getLookin().equals(SearchProperties.LOOKIN_CURRENT_SERVICE))
           {
               location =getCurrentServiceRoot();
           }
           else
           {
               location = ((CircabcNavigationBean) navigator).getCurrentNode().getNodeRef();
           }
           
           properties.setLocation(location);
       }

       @Override
       protected String finishImpl(FacesContext context, String outcome) throws Exception
       {
           // apply the alfresco search options
           super.search();

           final SearchContext search = navigator.getSearchContext();
           final SearchProperties circabcProperties = (SearchProperties) super.properties;

           search.setForceAndTerms(forceAnd);

           if (circabcProperties.getStatus() != null && !circabcProperties.getStatus().equals(SearchProperties.ANY_VALUE))
           {
               search.addFixedValueQuery(PROP_STATUS, circabcProperties.getStatus());
           }
           if (circabcProperties.getSecurityRanking() != null && !circabcProperties.getSecurityRanking().equals(SearchProperties.ANY_VALUE))
           {
               search.addFixedValueQuery(PROP_SECURITY_RANKING, circabcProperties.getSecurityRanking());
           }

           if (circabcProperties.getReference() != null && circabcProperties.getReference().length() != 0)
           {
               search.addAttributeQuery(PROP_REFERENCE, circabcProperties.getReference());
           }

           if (circabcProperties.getUrl() != null && circabcProperties.getUrl().length() != 0)
           {
               search.addAttributeQuery(PROP_URL, circabcProperties.getUrl());
           }

           if (circabcProperties.isIssueDateChecked())
           {
                 final SimpleDateFormat df = CachingDateFormat.getDateFormat();
                 final String strIssueDateFrom = df.format(circabcProperties.getIssueDateFrom());
                 final String strIssueDateTo = df.format(circabcProperties.getIssueDateTo());
                 search.addRangeQuery(PROP_ISSUE_DATE, strIssueDateFrom, strIssueDateTo, true);
           }
           if (circabcProperties.isExpirationDateChecked())
           {
                 final SimpleDateFormat df = CachingDateFormat.getDateFormat();
                 final String strExpDateFrom = df.format(circabcProperties.getExpirationDateFrom());
                 final String strExpDateTo = df.format(circabcProperties.getExpirationDateTo());
                 search.addRangeQuery(PROP_EXPIRATION_DATE, strExpDateFrom, strExpDateTo, true);
           }

           // category path search
           if (circabcProperties.getKeywords() != null && circabcProperties.getKeywords().size() > 0)
           {
        	   final List<Keyword> keywords = circabcProperties.getKeywords();
        	   final int keySize = keywords.size();

        	   final char wildcard = '*';

        	   // use this workaround because the search context provided by alfresco can only
        	   // build query with ONE search criteria by qname. But here, we need one cunjunction by keyword.
        	   final StringBuffer uglyWorkaround = new StringBuffer("");

        	   NodeRef keywordRef = null;
        	   for(int x = 0; x < keySize; ++x)
        	   {
        		   keywordRef = keywords.get(x).getId();

        		   if(x == 0)
        		   {
        			   uglyWorkaround
        			   		.append(wildcard)
        			   		.append(keywordRef.toString());

        		   }
        		   else
        		   {
        			   uglyWorkaround
   			   				.append(wildcard)
   			   				.append('"')
   			   				.append(" AND  +@")
   			   				.append(QueryParser.escape(DocumentModel.PROP_KEYWORD.toString()))
   			   				.append(':')
   			   				.append('"')
   			   				.append(wildcard)
   			   				.append(keywordRef.toString());
        		   }
        	   }

        	   uglyWorkaround.append(wildcard);

        	   search.addFixedValueQuery(PROP_KEYWORD, uglyWorkaround.toString());
           }

           if (circabcProperties.getSelectedLanguageOption() != null && !circabcProperties.getSelectedLanguageOption().equals(SearchProperties.LANGUAGE_ALL))
           {
        	   if(circabcProperties.getSelectedLanguageOption().equals(SearchProperties.LANGUAGE_CURRENT))
               {
        		   final String userLang = getUserPreferencesBean().getContentFilterLanguage();

        		   if(UserPreferencesBean.MSG_CONTENTALLLANGUAGES.equalsIgnoreCase(userLang))
        		   {
        			   circabcProperties.setLanguage(null);
        		   }
        		   else
        		   {
        			   circabcProperties.setLanguage(userLang);
        		   }

               }

               if(circabcProperties.getLanguage() != null)
               {
            	   search.addAttributeQuery(PROP_LOCALE, '*' + circabcProperties.getLanguage() + '*');
               }
           }

           setDynamicProperty(PROP_DYN_ATTR_1, circabcProperties.getDynamicProperty1(), search);
           setDynamicProperty(PROP_DYN_ATTR_2, circabcProperties.getDynamicProperty2(), search);
           setDynamicProperty(PROP_DYN_ATTR_3, circabcProperties.getDynamicProperty3(), search);
           setDynamicProperty(PROP_DYN_ATTR_4, circabcProperties.getDynamicProperty4(), search);
           setDynamicProperty(PROP_DYN_ATTR_5, circabcProperties.getDynamicProperty5(), search);
           setDynamicProperty(PROP_DYN_ATTR_6, circabcProperties.getDynamicProperty6(), search);
           setDynamicProperty(PROP_DYN_ATTR_7, circabcProperties.getDynamicProperty7(), search);
           setDynamicProperty(PROP_DYN_ATTR_8, circabcProperties.getDynamicProperty8(), search);
           setDynamicProperty(PROP_DYN_ATTR_9, circabcProperties.getDynamicProperty9(), search);
           setDynamicProperty(PROP_DYN_ATTR_10, circabcProperties.getDynamicProperty10(), search);
           
           setDynamicProperty(PROP_DYN_ATTR_11, circabcProperties.getDynamicProperty11(), search);
           setDynamicProperty(PROP_DYN_ATTR_12, circabcProperties.getDynamicProperty12(), search);
           setDynamicProperty(PROP_DYN_ATTR_13, circabcProperties.getDynamicProperty13(), search);
           setDynamicProperty(PROP_DYN_ATTR_14, circabcProperties.getDynamicProperty14(), search);
           setDynamicProperty(PROP_DYN_ATTR_15, circabcProperties.getDynamicProperty15(), search);
           setDynamicProperty(PROP_DYN_ATTR_16, circabcProperties.getDynamicProperty16(), search);
           setDynamicProperty(PROP_DYN_ATTR_17, circabcProperties.getDynamicProperty17(), search);
           setDynamicProperty(PROP_DYN_ATTR_18, circabcProperties.getDynamicProperty18(), search);
           setDynamicProperty(PROP_DYN_ATTR_19, circabcProperties.getDynamicProperty19(), search);
           setDynamicProperty(PROP_DYN_ATTR_20, circabcProperties.getDynamicProperty20(), search);


           if(circabcProperties.getSelectedDeepOption() != null && !circabcProperties.getSelectedDeepOption().equals(SearchProperties.DEEP_OPTION_LATEST_VERSION))
           {
        	   throw new NoSuchAlgorithmException("Perform search on the versions store is not implemented yet !!!");
           }

           NodeRef location = null;

           // location path search
           if (circabcProperties.getLookin().equals(SearchProperties.LOOKIN_INTEREST_GROUP))
           {
               location = ((CircabcNavigationBean) navigator).getCurrentIGRoot().getNodeRef();
           }
           else if (circabcProperties.getLookin().equals(SearchProperties.LOOKIN_CURRENT_SERVICE))
           {
               location =getCurrentServiceRoot();
           }
           else
           {
               location = ((CircabcNavigationBean) navigator).getCurrentNode().getNodeRef();
           }

           // don't use the the folder and content type
           circabcProperties.setFolderType(null);
           circabcProperties.setContentType(null);

           search.setLocation(SearchContext.getPathFromSpaceRef(location, true));

           Beans.getSearchResultDialog().reset();
           if (properties.getSavedSearch().equalsIgnoreCase(NO_SELECTION) )
           { 
        	   Beans.getSearchResultDialog().setNew(true);
           }
           else
           {
        	   Beans.getSearchResultDialog().setNew(false);
           }

           return SearchResultDialog.WAI_DIALOG_CALL;
       }
       
       /**
        * Select the saved searches and filter out the ones that do not belong 
        * to this IG
        */
       public List<SelectItem> getSavedSearches() {
    	   
    	   String savedSearchMode = properties.getSavedSearchMode();
    	   boolean searchSaveGlobal = properties.isSearchSaveGlobal();
    	   List<SelectItem> savedSearches = new ArrayList<SelectItem>();
    	   try 
    	   {
	    	   properties.getCachedSavedSearches().put(null);
	    	   properties.setSavedSearchMode(SAVED_SEARCHES_USER ); 
	    	   List<SelectItem> userSavedSearches = super.getSavedSearches();
	
	    	   properties.getCachedSavedSearches().put(null);
	    	   properties.setSavedSearchMode(SAVED_SEARCHES_GLOBAL);
	    	   List<SelectItem> globalSavedSearches = super.getSavedSearches();
	    	   
	    	   
	    	   String locationRef = null ;
	    	   
	    	   if (properties.getLocation() != null)
	    	   {
	    		   locationRef= properties.getLocation().toString();
	    	   }
	    	   
	    	   for (SelectItem savedSearch : userSavedSearches) {
	    		   
	    		   if ("NONE".equals((String) savedSearch.getValue())) {
	    			   savedSearches.add(savedSearch);
	    			   continue;
	    		   }
	    		   
	    		   NodeRef searchSearchRef = new NodeRef(Repository.getStoreRef(), 
	    				   						(String) savedSearch.getValue());
	    		   
	    		   String locationSearch = (String) getNodeService().getProperty(
	    				   		searchSearchRef, CircabcModel.PROP_LOCATION);
	    		   
	    		   // The null case is in case the search was saved before this change
	    		   // It will not have the property set
	    		   if (locationSearch == null || locationRef == null || locationRef.equals(locationSearch)) {
	    			   savedSearches.add(savedSearch);
	    		   }
	    	   }
	    	   
	    	   
	    	   for (SelectItem savedSearch : globalSavedSearches) {
	    		   
	    		   if ("NONE".equals((String) savedSearch.getValue())) {
	    			   continue;
	    		   }
	    		   
	    		   NodeRef searchSearchRef = new NodeRef(Repository.getStoreRef(), 
	    				   						(String) savedSearch.getValue());
	    		   
	    		   String locationSearch = (String) getNodeService().getProperty(
	    				   		searchSearchRef, CircabcModel.PROP_LOCATION);
	    		   
	    		   // The null case is in case the search was saved before this change
	    		   // It will not have the property set
	    		   if (locationSearch == null || locationRef == null || locationRef.equals(locationSearch)) {
	    			   savedSearches.add(savedSearch);
	    		   }
	    	   }
    	   }
    	   finally 
    	   {
    		   properties.setSavedSearchMode(savedSearchMode);
    		   properties.setSearchSaveGlobal(searchSaveGlobal); 
    	   }
    	   return savedSearches;
       }
       
       
       @Override
       public void selectSearch(ActionEvent event)
       {
    	  /*
    	   * Ugly workaround, we call twice the load but it is the best way to not 
    	   * maintain a pack of copy - pasted code.
    	   *  
    	   **/ 
    	   
    	  if (NO_SELECTION.equals(properties.getSavedSearch()) == false)
          {
             // read an XML serialized version of the SearchContext object
             final NodeRef searchSearchRef = new NodeRef(Repository.getStoreRef(), properties.getSavedSearch());
             final ServiceRegistry services = Repository.getServiceRegistry(FacesContext.getCurrentInstance());
             final ContentService cs = services.getContentService();
             try
             {
                if (services.getNodeService().exists(searchSearchRef))
                {
                   final ContentReader reader = cs.getReader(searchSearchRef, ContentModel.PROP_CONTENT);
                   final String xml = reader.getContentString();
                   final SearchContext search = new SearchContext().fromXML(xml);
                   final SearchContext delegate = new SearchContextDelegate(search).fromXML(xml);

                   this.forceAnd = delegate.getForceAndTerms();

                }
             }
             catch (Throwable ignore)
             {
             }
             finally
             {
            	 super.selectSearch(event);            	             	 
             }
          }
       }

       @Override
       public void reset(ActionEvent event)
       {
           super.reset(event);
           resetFields();

           Beans.getSearchResultDialog().reset();
       }

       private void resetFields()
       {
           final SearchProperties circabcProperties = (SearchProperties) super.properties;

           circabcProperties.setSecurityRankings(null);
           circabcProperties.setStatuses(null);
           circabcProperties.setKeywords(null);
           circabcProperties.setLanguages(null);
           circabcProperties.setStatus(null);
           circabcProperties.setReference(null);
           circabcProperties.setSecurityRanking(null);
           circabcProperties.setIssueDateChecked(false);
           circabcProperties.setExpirationDateChecked(false);
           circabcProperties.setIssueDateFrom(null);
           circabcProperties.setIssueDateTo(null);
           circabcProperties.setExpirationDateFrom(null);
           circabcProperties.setExpirationDateTo(null);
           circabcProperties.setDynamicProperty1(null);
           circabcProperties.setDynamicProperty2(null);
           circabcProperties.setDynamicProperty3(null);
           circabcProperties.setDynamicProperty4(null);
           circabcProperties.setDynamicProperty5(null);
           circabcProperties.setDynamicProperty6(null);
           circabcProperties.setDynamicProperty7(null);
           circabcProperties.setDynamicProperty8(null);
           circabcProperties.setDynamicProperty9(null);
           circabcProperties.setDynamicProperty10(null);
           circabcProperties.setDynamicProperty11(null);
           circabcProperties.setDynamicProperty12(null);
           circabcProperties.setDynamicProperty13(null);
           circabcProperties.setDynamicProperty14(null);
           circabcProperties.setDynamicProperty15(null);
           circabcProperties.setDynamicProperty16(null);
           circabcProperties.setDynamicProperty17(null);
           circabcProperties.setDynamicProperty18(null);
           circabcProperties.setDynamicProperty19(null);
           circabcProperties.setDynamicProperty20(null);
           circabcProperties.setUrl(null);
           circabcProperties.setLanguage(null);
           circabcProperties.setSelectedDeepOption(SearchProperties.DEEP_OPTION_LATEST_VERSION);
           circabcProperties.setSelectedLanguageOption(SearchProperties.LANGUAGE_ALL);
           circabcProperties.setLookin(SearchProperties.LOOKIN_INTEREST_GROUP);           
       }

       private NodeRef getCurrentServiceRoot()
       {
    	   final NavigableNode service = ((CircabcNavigationBean) navigator).getCurrentIGService();

    	   return (NodeRef) service.get(IGServicesNode.SERVICE_ROOT);
       }

       /**
        * @return Returns a list of different security rankings values to allow the user to select from
        */
       public List<SelectItem> getSecurityRankings()
       {
              final SearchProperties circabcProperties = (SearchProperties) super.properties;

              if ((circabcProperties.getSecurityRankings() == null) || (Application.isDynamicConfig(FacesContext.getCurrentInstance())))
              {
                  circabcProperties.setSecurityRankings(new ArrayList<SelectItem>(5));

                  for (final String securityRanking : SECURITY_RANKINGS)
                  {
                      circabcProperties.getSecurityRankings().add(new SelectItem(securityRanking, translate(securityRanking.toLowerCase())));
                  }
              }

              return circabcProperties.getSecurityRankings();
       }


       /**
         * @return the keywords as a display string
         */
        public String getDisplayKeywords()
        {
            return KeywordConverter.getDisplayString(FacesContext.getCurrentInstance(), getKeywords());
        }

        /**
         * @return the keywords
         */
        public List<Keyword> getKeywords()
        {
            final SearchProperties circabcProperties = (SearchProperties) super.properties;
            return circabcProperties.getKeywords();
        }

        /**
    	 * Change listener for the method select box
    	 */
    	public void updateSavedSearch(ValueChangeEvent event)
        {
    		final String newValue = (String) event.getNewValue();
    		if(newValue == null)
    		{
    			return;
    		}

    		setSavedSearch(newValue);
    		selectSearch(null);

    		resetFields();

    		final SearchProperties circabcProperties = (SearchProperties) properties;
    		final Map<String, Object> additionalProperties = properties.getCustomProperties();

    		if((Boolean) additionalProperties.get(PROP_EXPIRATION_DATE.toString()) != null)
    		{
    			circabcProperties.setExpirationDateChecked((Boolean) additionalProperties.get(PROP_EXPIRATION_DATE.toString()));
    			circabcProperties.setExpirationDateFrom((Date) additionalProperties.get(UISearchCustomProperties.PREFIX_DATE_FROM + PROP_EXPIRATION_DATE.toString()));
        		circabcProperties.setExpirationDateTo((Date) additionalProperties.get(UISearchCustomProperties.PREFIX_DATE_TO +  PROP_EXPIRATION_DATE.toString()));
    		}

    		if((Boolean) additionalProperties.get(PROP_ISSUE_DATE.toString()) != null)
    		{
    			circabcProperties.setIssueDateChecked((Boolean) additionalProperties.get(PROP_ISSUE_DATE.toString()));
    			circabcProperties.setIssueDateFrom((Date) additionalProperties.get(UISearchCustomProperties.PREFIX_DATE_FROM + PROP_ISSUE_DATE.toString()));
    			circabcProperties.setIssueDateTo((Date) additionalProperties.get(UISearchCustomProperties.PREFIX_DATE_TO +  PROP_ISSUE_DATE.toString()));
    		}

    		if(additionalProperties.get(PROP_LOCALE.toString()) != null)
    		{
    			final String lang = (String) additionalProperties.get(PROP_LOCALE.toString());
    			final int length = lang.length();

    			if(length > 2 )
    			{
    				circabcProperties.setSelectedLanguageOption(SearchProperties.LANGUAGE_SPECIFY);
    				circabcProperties.setLanguage(lang.substring(1, length - 1));
    			}
    		}
    		
    		circabcProperties.setSecurityRanking((String) additionalProperties.get(UISearchCustomProperties.PREFIX_LOV_ITEM + PROP_SECURITY_RANKING.toString()));
    		circabcProperties.setStatus((String) additionalProperties.get(UISearchCustomProperties.PREFIX_LOV_ITEM + PROP_STATUS.toString()));
    		circabcProperties.setReference((String) additionalProperties.get(PROP_REFERENCE.toString()));
    		circabcProperties.setUrl((String) additionalProperties.get(PROP_URL.toString()));
    		circabcProperties.setKeywords(parseKeywordQuery(additionalProperties.get(PROP_KEYWORD.toString())));
    		
    		// !!! don't save or restaure the dynamic properties !!! // Why?
    		// Probably because dynamic properties are placeholders and users can update them invalidating saved searches?
    		// It has been decided that this will be responsability of the user
    		// Another approach would be to invalidate all saved searches
    		
    		circabcProperties.setDynamicProperty1((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_1.toString()));
    		circabcProperties.setDynamicProperty2((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_2.toString()));
    		circabcProperties.setDynamicProperty3((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_3.toString()));
    		circabcProperties.setDynamicProperty4((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_4.toString()));
    		circabcProperties.setDynamicProperty5((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_5.toString()));
    		circabcProperties.setDynamicProperty6((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_6.toString()));
    		circabcProperties.setDynamicProperty7((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_7.toString()));
    		circabcProperties.setDynamicProperty8((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_8.toString()));
    		circabcProperties.setDynamicProperty9((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_9.toString()));
    		circabcProperties.setDynamicProperty10((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_10.toString()));
    		circabcProperties.setDynamicProperty11((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_11.toString()));
    		circabcProperties.setDynamicProperty12((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_12.toString()));
    		circabcProperties.setDynamicProperty13((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_13.toString()));
    		circabcProperties.setDynamicProperty14((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_14.toString()));
    		circabcProperties.setDynamicProperty15((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_15.toString()));
    		circabcProperties.setDynamicProperty16((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_16.toString()));
    		circabcProperties.setDynamicProperty17((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_17.toString()));
    		circabcProperties.setDynamicProperty18((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_18.toString()));
    		circabcProperties.setDynamicProperty19((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_19.toString()));
    		circabcProperties.setDynamicProperty20((Serializable) additionalProperties.get(DocumentModel.PROP_DYN_ATTR_20.toString()));
        }
    	
		public String getSavedSearch()
        {
            return super.properties.getSavedSearch();
        }

        public void setSavedSearch(String savedSearch)
        {
        	super.properties.setSavedSearch(savedSearch);
        }

        public boolean isLookinCurrentLocationDisable()
        {
            final NavigableNodeType nodeType = ((CircabcNavigationBean) navigator).getCurrentNodeType();

            return nodeType == null || !nodeType.isStrictlyUnder(NavigableNodeType.IG_ROOT);
        }

        /**
         * @param keywords to set
         */
        public void setKeyword(List<Keyword> keywords)
        {
            final SearchProperties circabcProperties = (SearchProperties) super.properties;
            circabcProperties.setKeywords(keywords);
        }

       /**
        * @return Returns a list of different status values to allow the user to select from
        */
       public List<SelectItem> getStatuses()
       {
              final SearchProperties circabcProperties = (SearchProperties) super.properties;

              if ((circabcProperties.getStatuses() == null) || (Application.isDynamicConfig(FacesContext.getCurrentInstance())))
              {
                  circabcProperties.setStatuses(new ArrayList<SelectItem>(5));

                  for (final String status : STATUS_VALUES)
                  {
                      circabcProperties.getStatuses().add(new SelectItem(status, translate(status.toLowerCase())));
                  }
              }

              return circabcProperties.getStatuses();
       }

       public List<DynamicProperty> getInterestGroupDynamicProperties()
       {
    	   final NavigableNode interestGroup = ((CircabcNavigationBean) navigator).getCurrentIGRoot();

    	   if(interestGroup == null)
    	   {
    		   return null;
    	   }
    	   else
    	   {
    		   return getDynamicPropertyService().getDynamicProperties(interestGroup.getNodeRef());
    	   }
       }

       /**
        * @return Returns a list of different languages options to allow the user to select from
        */
       public List<SelectItem> getLanguages()
       {
              final SearchProperties circabcProperties = (SearchProperties) super.properties;

              if ((circabcProperties.getLanguages() == null) || (Application.isDynamicConfig(FacesContext.getCurrentInstance())))
              {
                  final SelectItem[] languagesAsArray = getUserPreferencesBean().getContentFilterLanguages(false);
                  circabcProperties.setLanguages(new ArrayList<SelectItem>(languagesAsArray.length));
                  Collections.addAll(circabcProperties.getLanguages(), languagesAsArray);
              }

              return circabcProperties.getLanguages();
       }

       protected String translate(final String key, final Object ... params)
       {
    	   return WebClientHelper.translate(key, params);
        }

       private List<Keyword> parseKeywordQuery(Object object)
       {
    	   if(object == null)
    	   {
    		   return null;
    	   }

    	   final StringTokenizer tokens = new StringTokenizer(object.toString(), "*", false);

    	   final List<Keyword> keywords = new ArrayList<Keyword>(tokens.countTokens());

    	   while(tokens.hasMoreTokens())
    	   {
    		   String token = tokens.nextToken();
    		   if(token.startsWith(SPACE_STRORE_STRING))
    		   {
    			   keywords.add(getKeywordsService().buildKeywordWithId(token));
    		   }
    	   }

    	   return keywords;
       }

       private void setDynamicProperty(final QName propQname, final Serializable value, final SearchContext search)
       {

    	   if(value != null && value.toString().trim().length() > 0)
	       {
    		   if( value instanceof Date)
	    	   {
    			   final SimpleDateFormat df = CachingDateFormat.getDateFormat();

    			   final Calendar calendarFrom = GregorianCalendar.getInstance();
    			   calendarFrom.setTime((Date) value);
    			   final Calendar calendarTo = (Calendar) calendarFrom.clone();

    			   calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
    			   calendarFrom.set(Calendar.MINUTE, 0);
    			   calendarFrom.set(Calendar.SECOND, 0);

    			   calendarTo.set(Calendar.HOUR_OF_DAY, 23);
    			   calendarTo.set(Calendar.MINUTE, 59);
    			   calendarTo.set(Calendar.SECOND, 59);

    			   search.addRangeQuery(propQname, df.format(calendarFrom.getTime()), df.format(calendarTo.getTime()), true);

	    	   }
	    	   else
	    	   {
	    		   search.addAttributeQuery(propQname, value.toString());
	    	   }
    	   }
       }

        @Override
   		protected String getDefaultCancelOutcome()
        {
        	return CircabcNavigationHandler.CLOSE_WAI_DIALOG_OUTCOME +
        				CircabcNavigationHandler.OUTCOME_SEPARATOR +
        				CircabcBrowseBean.PREFIXED_WAI_BROWSE_OUTCOME;
        }

        @Override
        public String getFinishButtonLabel()
        {
            return translate("search");
        }

        @Override
        public boolean getFinishButtonDisabled()
        {
            return false;
        }

        public ActionsListWrapper getActionList()
        {
            return null;
        }

        public String getBrowserTitle()
        {
            return translate("advanced_search_dialog_browser_title");
        }

        public String getPageIconAltText()
        {
            return translate("advanced_search_dialog_icon_tooltip");
        }

        public boolean isCancelButtonVisible()
        {
            return true;
        }

        public boolean isFormProvided()
        {
            return false;
        }

        /**
         * @return the userPreferencesBean
         */
        protected final CircabcUserPreferencesBean getUserPreferencesBean()
        {
            if(userPreferencesBean == null)
            {
                userPreferencesBean = Beans.getUserPreferencesBean();
            }
            return userPreferencesBean;
        }

        /**
         * @param userPreferencesBean the userPreferencesBean to set
         */
        public final void setUserPreferencesBean(CircabcUserPreferencesBean userPreferencesBean)
        {
            this.userPreferencesBean = userPreferencesBean;
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
		 * @return the dynamicPropertyService
		 */
		protected final DynamicPropertyService getDynamicPropertyService()
		{
			if(dynamicPropertyService == null)
			{
				dynamicPropertyService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getDynamicPropertieService();
			}
			return dynamicPropertyService;
		}

		/**
		 * @param dynamicPropertyService the dynamicPropertyService to set
		 */
		public final void setDynamicPropertyService(DynamicPropertyService dynamicPropertyService)
		{
			this.dynamicPropertyService = dynamicPropertyService;
		}

		/**
		 * @return the forceAnd
		 */
		public final boolean isForceAnd()
		{
			return forceAnd;
		}

		/**
		 * @param forceAnd the forceAnd to set
		 */
		public final void setForceAnd(boolean forceAnd)
		{
			this.forceAnd = forceAnd;
		}
}



