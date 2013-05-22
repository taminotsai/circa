/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.dialog.permissions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import net.sf.ehcache.CacheManager;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AccessStatus;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.util.ISO9075;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.PermissionUtils;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;

/**
 * Bean that backs manage permissions dialog (for any kind of node).
 *
 * @author Yanick Pignot
 */
public class ManagePermissionsDialog extends BaseWaiDialog
{
	private static final long serialVersionUID = 880410868117140631L;

	private static final Log logger = LogFactory.getLog(ManagePermissionsDialog.class);

	public static final String BEAN_NAME = "ManagePermissionsDialog";

	private transient OwnableService ownableService;

	private static final String MSG_SUCCESS_INHERIT_NOT = "success_not_inherit_permissions";
	private static final String MSG_SUCCESS_INHERIT = "success_inherit_permissions";

	private boolean inheritPermissions;

	/** The invited person in this space */
	private List<Map> personNodes = null;

	private CacheManager dynamicAuthorithiesCacheManager;
	
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null && getActionNode() == null)
		{
			throw new IllegalArgumentException("The node id is a mandatory parameter");
		}

		inheritPermissions = this.getPermissionService().getInheritParentPermissions(getActionNode().getNodeRef());
	}

	public List<Map> getUsers()
    {
		personNodes = null;
		personNodes = PermissionUtils.getInterestGroupAuthorities(getActionNode().getNodeRef());

		if (logger.isDebugEnabled())
		{
			logger.debug(personNodes.size() + " users or access/profile are re-defined in space " + getActionNode().getName());
		}

		return personNodes;
   }


   /**
    * Return the owner username
    */
   public String getOwner()
   {
      return this.getOwnableService().getOwner(getActionNode().getNodeRef());
   }

   public void inheritPermissionsValueDeepTree(ActionEvent event)
   {

	   	NodeRef currentNode = getActionNode().getNodeRef();
         // change the value to the new selected value
         final Boolean inheritParentPermissions = this.getPermissionService().getInheritParentPermissions(currentNode);
         
         this.getPermissionService().setInheritParentPermissions(currentNode, !inheritParentPermissions);
         
         String query = "(PATH:\"" + getLucenePathFromSpaceRef(currentNode) + "/*\")";
         query += "AND (TYPE:\"" + ContentModel.TYPE_FOLDER + "\")";
         
         ResultSet results = null;
         try
         {
             results = this.getSearchService().query(currentNode.getStoreRef(), SearchService.LANGUAGE_LUCENE, query);
            for (ResultSetRow resultSetRow : results) {
				this.getPermissionService().setInheritParentPermissions(resultSetRow.getNodeRef(), !inheritParentPermissions);
			}

         }
         finally
         {
             if(results != null)
             {
                 results.close();
             }
         } 
         

   }
   
	public void inheritPermissionsValueChanged(ActionEvent event)
	{
		try
	    {
	         // change the value to the new selected value
	         this.getPermissionService().setInheritParentPermissions(getActionNode().getNodeRef(), inheritPermissions);

	         // inform the user that the change occured
	         FacesContext context = FacesContext.getCurrentInstance();
	         String msg;
	         if (inheritPermissions)
	         {
	            msg = translate(MSG_SUCCESS_INHERIT);
	         }
	         else
	         {
	            msg = translate(MSG_SUCCESS_INHERIT_NOT);
	         }

	         // see if the user still has permissions to the node, if not, we need
	         // to go back to the root of the current "area" by simulating the user
	         // pressing the top level navigation button i.e. My Home
	         if (this.getPermissionService().hasPermission(getActionNode().getNodeRef(), PermissionService.CHANGE_PERMISSIONS) == AccessStatus.ALLOWED)
	         {
	            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
	            context.addMessage(event.getComponent().getClientId(context), facesMsg);
	         }
	         else
	         {
	            Beans.getWaiBrowseBean().clickCircabcHome();
	         }
	         
	         PermissionUtils.resetCache(getActionNode(), dynamicAuthorithiesCacheManager, logger);
	      }
	      catch (Throwable e)
	      {
	         Utils.addErrorMessage(translate( Repository.ERROR_GENERIC, e.getMessage()), e);
	      }

		if(logger.isDebugEnabled())
		{
			logger.debug("The inherit permission property is succesffully setted as " + inheritPermissions + " for the space " + getActionNode().getName());
		}
	}

	/**
	 * @return true if the current user can change permissions on this Space
	 */
	public boolean getHasChangePermissions()
	{
	    return getActionNode().hasPermission(PermissionService.CHANGE_PERMISSIONS);
	}


	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return outcome;
	}

	@Override
	public String getContainerTitle()
	{
	     return translate("manage_space_permission_page_tilte", getActionNode().getName());
	}

	@Override
	public String getContainerDescription()
	{
	   return translate("manage_space_permission_page_description");
	}

	@Override
	public String getCancelButtonLabel()
	{
	   return translate("close");
	}

	@Override
	public void restored()
	{
	}

	public String getBrowserTitle()
	{
		return translate("manage_space_permission_page_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_space_permission_page_icon_tooltip");
	}


	/**
	 * @return the ownableService
	 */
	protected final OwnableService getOwnableService()
	{
		if(ownableService == null)
		{
			ownableService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getOwnableService();
		}
		return ownableService;
	}

	/**
	 * @param ownableService the ownableService to set
	 */
	public final void setOwnableService(OwnableService ownableService)
	{
		this.ownableService = ownableService;
	}

	/**
	 * @return the inheritPermissions
	 */
	public final boolean isInheritPermissions()
	{
		return inheritPermissions;
	}

	/**
	 * @param inheritPermissions the inheritPermissions to set
	 */
	public final void setInheritPermissions(boolean inheritPermissions)
	{
		this.inheritPermissions = inheritPermissions;
	}

	/**
	 * @param dynamicAuthorithiesCacheManager the dynamicAuthorithiesCacheManager to set
	 */
	public final void setDynamicAuthorithiesCacheManager(CacheManager dynamicAuthorithiesCacheManager)
	{
		this.dynamicAuthorithiesCacheManager = dynamicAuthorithiesCacheManager;
	}
	
	private String getLucenePathFromSpaceRef(NodeRef spaceRef) {
		
		final Path path = getNodeService().getPath(spaceRef);
		final StringBuilder buf = new StringBuilder(64);
		String elementString;
		Path.Element element;
		ChildAssociationRef elementRef;
		Collection<?> prefixes;
		for (int i = 0; i < path.size(); i++)
		{
			elementString = "";
			element = path.get(i);
			if (element instanceof Path.ChildAssocElement)
			{
				elementRef = ((Path.ChildAssocElement) element).getRef();
				if (elementRef.getParentRef() != null)
				{
					prefixes = getNamespaceService().getPrefixes(elementRef.getQName().getNamespaceURI());
					if (prefixes.size() > 0)
					{
						elementString = '/' + (String) prefixes.iterator().next() + ':' + ISO9075.encode(elementRef.getQName().getLocalName());
					}
				}
			}

			buf.append(elementString);
		}

			buf.append("/");


		return buf.toString();
	}


}
