/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.part;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.TypeDefinition;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.config.CircabcConfiguration;
import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.AspectResolver;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNode;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.bean.override.CircabcNavigationBean;
import eu.cec.digit.circabc.web.repository.IGServicesNode;
import eu.cec.digit.circabc.web.repository.InterestGroupNode;

/**
 * Bean that back the left menu.
 *
 * @author yanick pignot
 */
public class LeftMenuBean implements Serializable
{
	private static final long serialVersionUID = -7581124182630093270L;

	public static final String BEAN_NAME = "WaiLeftMenuBean";

	private static String MSG_EDIT_PROPERTIES_SPACE = "edit_space_action_title";
	private static String MSG_EDIT_PROPERTIES_FORUM = "edit_discussion_action_title";
	private static String MSG_EDIT_PROPERTIES_CIRCABC = "edit_circabc_action_title";
	private static String MSG_EDIT_PROPERTIES_CATEGORY = "edit_category_action_title";
	private static String MSG_EDIT_PROPERTIES_IGROOT = "edit_igroot_action_title";
	private static String MSG_EDIT_PROPERTIES_LIBRARY = "edit_library_action_title";
	private static String MSG_EDIT_PROPERTIES_NEWSGROUP = "edit_newsgroup_action_title";
	private static String MSG_EDIT_PROPERTIES_SURVEY = "edit_survey_action_title";
	private static String MSG_EDIT_PROPERTIES_INFORMATION = "edit_information_action_title";
	private static String MSG_EDIT_PROPERTIES_EVENT = "edit_event_action_title";
	private static String MSG_EDIT_PROPERTIES_HEADER = "edit_header_action_title";
	
	private static String hostName = null;
	private static String isDisplayServerInformationActive = null;


	private CircabcNavigationBean navigator;
	private transient DictionaryService dictionaryService;

	public void doNothingAction(ActionEvent event)
	{

	}

	/**
	 * @return true if the menu must display le IG part of the menu
	 */
	public boolean isInterestGroupDisplay()
	{
		return getIGNode() != null && getIGNode() != null;
	}

	/**
	 * @return true if the current ig has a library service (and user has right to see it)
	 */
	public boolean isLibraryDisplay()
	{
		return getIGNode() != null && getIGNode().getLibrary() != null;
	}

	/**
	 * @return true if the current ig has a directory service (and user has right to see it)
	 */
	public boolean isDirectoryDisplay()
	{
		return getIGNode() != null && getIGNode().getDirectory() != null;
	}

	/**
	 * @return true if the current ig has a newsgroup service (and user has right to see it)
	 */
	public boolean isNewsgroupDisplay()
	{
		return getIGNode() != null && getIGNode().getNewsgroup() != null;
	}

	/**
	 * @return true if the current ig has a survey service (and user has right to see it)
	 */
	public boolean isSurveyDisplay()
	{
		return getIGNode() != null && getIGNode().getSurvey() != null;
	}

	/**
	 * @return true if the current ig has a information service (and user has right to see it)
	 */
	public boolean isInformationDisplay()
	{
		
		return getIGNode() != null && getIGNode().getInformation() != null;
	}

	/**
	 * @return true if the current ig has a Event service (and user has right to see it)
	 */
	public boolean isEventDisplay()
	{
		return getIGNode() != null && getIGNode().getEvent() != null;
	}

	/**
     * @return true if the current browsable node is a Library root or a children of it
     */
    public boolean isLibraryActivated()
    {
	   final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get(IGServicesNode.IS_LIBRARY));
    }

    /**
     * @return true if the current browsable node is a newsgroup root or a children of it
     */
    public boolean isNewsgroupActivated()
    {
       final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get("isNewsgroupChild"));
    }

    /**
     * @return true if the current browsable node is a Survey root or a children of it
     */
    public boolean isSurveyActivated()
    {
       final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get("isSurveyChild"));
    }

    /**
     * @return true if the current browsable node is a Directory root or a children of it
     */
    public boolean isDirectoryActivated()
    {
       final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get("isDirectoryChild"));
    }

    /**
     * @return true if the current browsable node is a Information root or a children of it
     */
    public boolean isInformationActivated()
    {
       final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get("isInformationChild"));
    }

    /**
     * @return true if the current browsable node is a Event root or a children of it
     */
    public boolean isEventActivated()
    {
       final NavigableNode service = getNavigator().getCurrentIGService();
	   return service != null && ((Boolean)service.get("isEventChild"));
    }

	/**
	 * Util method that get a Label for Edit Space Properties action label differently according
	 * the type of the current node.
	 *
	 * @return 	the right label for the edit space properties action
	 */
	public String getEditPropertiesLabel()
	{
		final FacesContext fc = FacesContext.getCurrentInstance();
		String label = null;
		final Node actionNode = Beans.getWaiBrowseBean().getActionSpace();
		NavigableNodeType type;

		if(actionNode != null)
		{
			type = AspectResolver.resolveType(actionNode);
		} else {
			type = getNavigator().getCurrentNodeType();
		}

		if(type == null)
		{
			label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_SPACE);
		}
		else
		{
			switch(type)
			{
				case LIBRARY:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_LIBRARY);
					break;
				case LIBRARY_FORUM:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_FORUM);
					break;
				case NEWSGROUP:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_NEWSGROUP);
					break;
				case INFORMATION:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_INFORMATION);
					break;
				case EVENT:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_EVENT);
					break;
				case SURVEY:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_SURVEY);
					break;
				case IG_ROOT:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_IGROOT);
					break;
				case CATEGORY:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_CATEGORY);
					break;
				case CIRCABC_ROOT:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_CIRCABC);
					break;
				case CATEGORY_HEADER:
					label = Application.getMessage(fc, 	MSG_EDIT_PROPERTIES_HEADER);
					break;
				default:
					label = Application.getMessage(fc, MSG_EDIT_PROPERTIES_SPACE);
			}
		}


		return label;
	}


	/**
	 * Return if the left menu displays the administration Service Menu
	 *
	 * @return true if the current user is not a guest
	 */
	public boolean isAdminView()
	{
		return getNavigator() != null && !getNavigator().getIsGuest();
	}

	/**
	 * @return the isDisplayServerInformationActive
	 */
	public boolean isDisplayServerInformationActive()
	{
		if (isDisplayServerInformationActive == null)
		{
			isDisplayServerInformationActive = CircabcConfiguration.getProperty(CircabcConfiguration.IS_DISPLAY_SERVER_INFORMATION_ACTIVATE);
		}
		return Boolean.valueOf(isDisplayServerInformationActive);
	}

	/**
	 * Helper that return the current ig node or null if the navigation is above it (Circabc, cat header or category)
	 */
	private InterestGroupNode getIGNode()
	{
		InterestGroupNode interestGroupNode = null;
		if(getNavigator() != null) {
			interestGroupNode = (InterestGroupNode) getNavigator().getCurrentIGRoot();
		}
		return interestGroupNode;
	}

	/**
	 * For instance, the clipboard can only be displayed under the Library.
	 * The current node must be a space.
	 *
	 * @return if the clipboard must be displayed or not.
	 */
	public boolean isPastEnableHere()
	{
		final NavigableNode service = getNavigator().getCurrentIGService();

		if(service == null || (!((Boolean)service.get(IGServicesNode.IS_LIBRARY)) && !((Boolean)service.get(IGServicesNode.IS_INFORMATION))))
		{
			return false;
		}
		else
		{
			final QName type = getNavigator().getCurrentNode().getType();

			// make sure the type is defined in the data dictionary
	        final TypeDefinition typeDef = getDictionaryService().getType(type);

	        if (typeDef != null)
	        {
	        	return ContentModel.TYPE_FOLDER.equals(type) || getDictionaryService().isSubClass(type, ContentModel.TYPE_FOLDER);
	        }
	        else
	        {
	        	return false;
	        }
		}
	}

	/**
	 * Return the hostname of the server
	 * @return
	 */
	public String getHostName() {
		if(hostName == null) {
			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (final UnknownHostException e) {
				hostName = "Cannot get computer name ";
			}
		}
		return hostName;
	}


	/**
	 * @return the navigator
	 */
	protected final CircabcNavigationBean getNavigator()
	{
		if(navigator == null)
		{
			navigator = Beans.getWaiNavigator();
		}
		return navigator;
	}

	/**
	 * @param navigator the navigator to set
	 */
	public final void setNavigator(CircabcNavigationBean navigator)
	{
		this.navigator = navigator;
	}

	/**
	 * @return the dictionaryService
	 */
	protected final DictionaryService getDictionaryService()
	{
		if(dictionaryService == null)
		{
			dictionaryService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getDictionaryService();
		}
		return dictionaryService;
	}

	/**
	 * @param dictionaryService the dictionaryService to set
	 */
	public final void setDictionaryService(DictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

}
