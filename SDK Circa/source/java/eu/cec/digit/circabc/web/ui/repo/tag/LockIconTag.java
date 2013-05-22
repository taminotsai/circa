/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * @author Guillaume
 */
public class LockIconTag extends HtmlComponentTag
{
   /**
    * @see javax.faces.webapp.UIComponentTag#getComponentType()
    */
   public String getComponentType()
   {
      return "eu.cec.digit.circabc.faces.LockIcon";
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#getRendererType()
    */
   public String getRendererType()
   {
      return null;
   }

   /**
    * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
    */
   protected void setProperties(UIComponent component)
   {
      super.setProperties(component);
      setStringProperty(component, "lockImage", this.lockImage);
      setStringProperty(component, "lockOwnerImage", this.lockOwnerImage);
      setStringProperty(component, "lockedOwnerTooltip", this.lockedOwnerTooltip);
      setStringProperty(component, "lockedUserTooltip", this.lockedUserTooltip);
      setStringBindingProperty(component, "value", this.value);
   }

   /**
    * @see org.alfresco.web.ui.common.tag.HtmlComponentTag#release()
    */
   public void release()
   {
      super.release();
      this.lockImage = null;
      this.lockOwnerImage = null;
      this.lockedOwnerTooltip = null;
      this.lockedUserTooltip = null;
      this.value = null;
   }

   /**
    * Set the lockImage
    *
    * @param lockImage     the lockImage
    */
   public void setLockImage(String lockImage)
   {
      this.lockImage = lockImage;
   }

   /**
    * Set the lockOwnerImage
    *
    * @param lockOwnerImage     the lockOwnerImage
    */
   public void setLockOwnerImage(String lockOwnerImage)
   {
      this.lockOwnerImage = lockOwnerImage;
   }

   /**
    * Set the value
    *
    * @param value     the value
    */
   public void setValue(String value)
   {
      this.value = value;
   }

   /**
    * Set the lockedOwnerTooltip
    *
    * @param lockedOwnerTooltip     the lockedOwnerTooltip
    */
   public void setLockedOwnerTooltip(String lockedOwnerTooltip)
   {
      this.lockedOwnerTooltip = lockedOwnerTooltip;
   }

   /**
    * Set the lockedUserTooltip
    *
    * @param lockedUserTooltip     the lockedUserTooltip
    */
   public void setLockedUserTooltip(String lockedUserTooltip)
   {
      this.lockedUserTooltip = lockedUserTooltip;
   }


   /** the lockedOwnerTooltip */
   private String lockedOwnerTooltip;

   /** the lockedUserTooltip */
   private String lockedUserTooltip;

   /** the lockImage */
   private String lockImage;

   /** the lockOwnerImage */
   private String lockOwnerImage;

   /** the value */
   private String value;
}
