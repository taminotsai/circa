/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.component;

import java.io.IOException;
import java.text.MessageFormat;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.el.ValueBinding;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.web.app.Application;
import org.alfresco.web.bean.repository.Repository;
import org.alfresco.web.ui.common.Utils;
import org.alfresco.web.ui.repo.WebResources;
import org.springframework.extensions.webscripts.ui.common.component.SelfRenderingComponent;
import org.springframework.util.StringUtils;

/**
 * @author Guillaume
 * 
 * Migration 3.1 -> 3.4.6 - 02/12/2011
 * SelfRenderingComponent was moved to Spring.
 * This class seems to be developed for CircaBC
 */
public class UILockIcon extends SelfRenderingComponent
{
   private static final String MSG_LOCKED_YOU  = "locked_you";
   private static final String MSG_LOCKED_USER = "locked_user";

   // ------------------------------------------------------------------------------
   // Component implementation

   /**
    * @see javax.faces.component.UIComponent#getFamily()
    */
   public String getFamily()
   {
      return "eu.cec.digit.circabc.faces.LockIcon";
   }

   /**
    * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
    */
   public void restoreState(FacesContext context, Object state)
   {
      Object values[] = (Object[])state;
      // standard component attributes are restored by the super class
      super.restoreState(context, values[0]);
      this.lockImage = (String)values[1];
      this.lockOwnerImage = (String)values[2];
      this.value = values[3];
   }

   /**
    * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
    */
   public Object saveState(FacesContext context)
   {
      return new Object[]
      {
         // standard component attributes are saved by the super class
         super.saveState(context),
         this.lockImage,
         this.lockOwnerImage,
         this.value
      };
   }

   /**
    * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
    */
   public void encodeBegin(FacesContext context) throws IOException
   {
      if (isRendered() == false)
      {
         return;
      }

      // get the value and see if the image is locked
      final String userName = AuthenticationUtil.getRunAsUser();
      try 
      {
    	  AuthenticationUtil.setRunAsUserSystem();
	      NodeService nodeService = getNodeService(context);
	      String lockUser = null;
	      Object val = getValue();
	      NodeRef ref = null;
	      if (val instanceof NodeRef)
	      {
	         ref = (NodeRef)val;
	         if (nodeService.exists(ref) && nodeService.hasAspect(ref, ContentModel.ASPECT_LOCKABLE) == true)
	         {
	            lockUser = (String)nodeService.getProperty(ref, ContentModel.PROP_LOCK_OWNER);
	         }
	      }
	      final boolean locked = lockUser != null;
	      final boolean lockedOwner = locked && (lockUser.equals(Application.getCurrentUser(context).getUserName()));

	      this.encodeBegin(context, locked, lockedOwner, new String[] { lockUser });
      }
      finally
      {
    	  AuthenticationUtil.setRunAsUser(userName);
      }
   }

   protected void encodeBegin(final FacesContext context,
                              final boolean locked,
                              final boolean lockedOwner,
                              final String[] lockUser)
      throws IOException
   {
      if (isRendered() == false)
      {
         return;
      }
      ResponseWriter out = context.getResponseWriter();
      String msg = null;

      if (locked == true)
      {
         out.write("&nbsp;<img");

         outputAttribute(out, getAttributes().get("styleClass"), "class");

         out.write(" src=\"");
         out.write(context.getExternalContext().getRequestContextPath());
         String lockImage = getLockImage();
         if (lockedOwner == true && getLockOwnerImage() != null)
         {
            lockImage = getLockOwnerImage();
         }
         out.write(lockImage);
         out.write("\"");

         if (lockedOwner == true)
         {
            msg = Application.getMessage(context, MSG_LOCKED_YOU);
            if (getLockedOwnerTooltip() != null)
            {
               msg = getLockedOwnerTooltip();
            }
         }
         else
         {
            msg = MessageFormat.format(Application.getMessage(context, MSG_LOCKED_USER), lockUser.length);
            if (getLockedUserTooltip() != null)
            {
               msg = getLockedUserTooltip();
            }
            StringBuilder buf = new StringBuilder(32);
            msg = buf.append(msg).append(" '")
                     .append(StringUtils.arrayToDelimitedString(lockUser, ", "))
                     .append("'").toString();
         }

         msg = Utils.encode(msg);
         out.write(" alt=\"");
         out.write(msg);
         out.write("\" title=\"");
         out.write(msg);
         out.write("\" />");
      }
   }

   /**
    * Use Spring JSF integration to return the Node Service bean instance
    *
    * @param context    FacesContext
    *
    * @return Node Service bean instance or throws exception if not found
    */
   private static NodeService getNodeService(FacesContext context)
   {
      NodeService service = Repository.getServiceRegistry(context).getNodeService();
      if (service == null)
      {
         throw new IllegalStateException("Unable to obtain NodeService bean reference.");
      }

      return service;
   }

   // ------------------------------------------------------------------------------
   // Strongly typed component property accessors

   /**
    * @return the image to display as the lock icon. A default is provided if none is set.
    */
   public String getLockImage()
   {
      ValueBinding vb = getValueBinding("lockImage");
      if (vb != null)
      {
         this.lockImage = (String)vb.getValue(getFacesContext());
      }

      return this.lockImage;
   }

   /**
    * @param lockImage     the image to display as the lock icon. A default is provided if none is set.
    */
   public void setLockImage(String lockImage)
   {
      this.lockImage = lockImage;
   }

   /**
    * @return Returns the image to display if the owner has the lock.
    */
   public String getLockOwnerImage()
   {
      ValueBinding vb = getValueBinding("lockOwnerImage");
      if (vb != null)
      {
         this.lockOwnerImage = (String)vb.getValue(getFacesContext());
      }

      return this.lockOwnerImage;
   }

   /**
    * @param lockOwnerImage     the image to display if the owner has the lock.
    */
   public void setLockOwnerImage(String lockOwnerImage)
   {
      this.lockOwnerImage = lockOwnerImage;
   }

   /**
    * @return Returns the lockedOwnerTooltip.
    */
   public String getLockedOwnerTooltip()
   {
      ValueBinding vb = getValueBinding("lockedOwnerTooltip");
      if (vb != null)
      {
         this.lockedOwnerTooltip = (String)vb.getValue(getFacesContext());
      }

      return this.lockedOwnerTooltip;
   }

   /**
    * @param lockedOwnerTooltip The lockedOwnerTooltip to set.
    */
   public void setLockedOwnerTooltip(String lockedOwnerTooltip)
   {
      this.lockedOwnerTooltip = lockedOwnerTooltip;
   }

   /**
    * @return Returns the lockedUserTooltip.
    */
   public String getLockedUserTooltip()
   {
      ValueBinding vb = getValueBinding("lockedUserTooltip");
      if (vb != null)
      {
         this.lockedUserTooltip = (String)vb.getValue(getFacesContext());
      }

      return this.lockedUserTooltip;
   }

   /**
    * @param lockedUserTooltip The lockedUserTooltip to set.
    */
   public void setLockedUserTooltip(String lockedUserTooltip)
   {
      this.lockedUserTooltip = lockedUserTooltip;
   }

   /**
    * @return Returns the value (Node or NodeRef)
    */
   public Object getValue()
   {
      ValueBinding vb = getValueBinding("value");
      if (vb != null)
      {
         this.value = vb.getValue(getFacesContext());
      }

      return this.value;
   }

   /**
    * @param value The Node or NodeRef value to set.
    */
   public void setValue(Object value)
   {
      this.value = value;
   }


   private String lockImage = WebResources.IMAGE_LOCK;
   private String lockOwnerImage = WebResources.IMAGE_LOCK_OWNER;
   private String lockedOwnerTooltip = null;
   private String lockedUserTooltip = null;
   private Object value = null;
}
