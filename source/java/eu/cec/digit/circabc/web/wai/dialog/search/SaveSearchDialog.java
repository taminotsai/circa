/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.search;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.bean.search.SearchContext;
import org.alfresco.web.ui.common.ReportedException;
import org.alfresco.web.ui.common.Utils;

import eu.cec.digit.circabc.model.CircabcModel;
import eu.cec.digit.circabc.web.wai.dialog.WaiDialog;
import eu.cec.digit.circabc.web.wai.manager.ActionsListWrapper;

/**
 * Bean that back the WAI save search.
 *
 * @author yanick pignot
 */
public class SaveSearchDialog extends org.alfresco.web.bean.search.SaveSearchDialog implements WaiDialog
{
       private static final long serialVersionUID = -8191901120224724444L;

       public static final String BEAN_NAME = "CircabcSaveSearchDialog";
       
       private static final String MSG_ERROR_SAVE_SEARCH = "error_save_search";
       
       
       @Override
       protected String finishImpl(FacesContext context, String outcome) throws Exception
       {
    	   properties.setSearchName(properties.getSearchName().trim());
    	   if (properties.getSearchName() == null || properties.getSearchName().trim().length() < 1)
   		   {
   				// Found forbidden character
   				Utils.addErrorMessage(Application.getMessage(context, "save_search_dialog_name_mandatory"));
   				return null;
   		   }
    	   
           return saveNewSearchOK(context, outcome);
       }
       
       /**
        * This method saves the search. It was copied and modified from the 
        * superclass to add the context nodeRef (IGRoot)
        */
       public String saveNewSearchOK(FacesContext newContext, String newOutcome)
       {
           String outcome = newOutcome;
           
           NodeRef searchesRef;
           
           
           if (properties.isSearchSaveGlobal())
           {
               searchesRef = getGlobalSearchesRef();
           }
           else
           {
               searchesRef = getUserSearchesRef();
           }

           final SearchContext search = this.navigator.getSearchContext();
           if (searchesRef != null && search != null)
           {
               try
               {
                   final FacesContext context = newContext;// FacesContext.getCurrentInstance();
                   final NodeRef searchesRefFinal = searchesRef;
                   
                   RetryingTransactionCallback<Object> callback = new RetryingTransactionCallback<Object>()
                   {
                       public Object execute() throws Throwable
                       {
                           // create new content node as the saved search object
                           Map<QName, Serializable> props = new HashMap<QName, Serializable>(2, 1.0f);
                           props.put(ContentModel.PROP_NAME, properties.getSearchName());
                           props.put(ContentModel.PROP_DESCRIPTION, properties.getSearchDescription());
                           ChildAssociationRef childRef = getNodeService().createNode(searchesRefFinal, ContentModel.ASSOC_CONTAINS, QName.createQName(NamespaceService.ALFRESCO_URI, QName
                                   .createValidLocalName(properties.getSearchName())), ContentModel.TYPE_CONTENT, props);
                           
                           ContentService contentService = Repository.getServiceRegistry(context).getContentService();
                           ContentWriter writer = contentService.getWriter(childRef.getChildRef(), ContentModel.PROP_CONTENT, true);
                           
                           // get a writer to our new node ready for XML content
                           writer.setMimetype(MimetypeMap.MIMETYPE_XML);
                           writer.setEncoding("UTF-8");
                           
                           // output an XML serialized version of the SearchContext
                           // object
                           writer.putContent(search.toXML());
                           
                           // This part was added to determine the context to
                           // save
                           
                           // Location root nodeRef. This nodeRef must be saved 
                           // into the search in order to know to which 
                           // location this saved search belongs
                    	   // This will avoid dynamic property collisions
                    	   NodeRef location = properties.getLocation();
                    	   
                    	   Map<QName, Serializable> aspectProperties = 
                    			   		new HashMap<QName, Serializable>(1);
                    	   aspectProperties.put(CircabcModel.PROP_LOCATION, 
                    			   					location.toString());
                    	   getNodeService().addAspect(childRef.getChildRef(), 
                    			   CircabcModel.ASPECT_SAVED_ROOT_SEARCHABLE, 
                    			   aspectProperties);
                    	   
                           return null;
                       }
                   };
                   callback.execute();
                   properties.getCachedSavedSearches().clear();
                   properties.setSavedSearch(null);
               }
               catch (Throwable e)
               {
                   Utils.addErrorMessage(MessageFormat.format(Application.getMessage(newContext, MSG_ERROR_SAVE_SEARCH), e.getMessage()), e);
                   outcome = null;
                   this.isFinished = false;
                   ReportedException.throwIfNecessary(e);
               }
           }
           
           return outcome;
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
	        return Application.getMessage(FacesContext.getCurrentInstance(), "save_search_dialog_browser_title");
	    }

	    public String getPageIconAltText()
	    {
	    	return Application.getMessage(FacesContext.getCurrentInstance(), "save_search_dialog_icon_tooltip");
	    }

	    public boolean isCancelButtonVisible()
	    {
	        return true;
	    }

	    public boolean isFormProvided()
	    {
	        return false;
	    }


}



