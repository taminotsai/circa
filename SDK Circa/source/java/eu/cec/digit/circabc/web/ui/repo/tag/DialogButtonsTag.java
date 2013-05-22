/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.ui.repo.tag;


import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * WAI.
 * Tag class that allows the UIDialogButtons component to be placed on a JSP.
 *
 * @author patrice.coppens@trasys.lu
 */
public class DialogButtonsTag extends HtmlComponentTag
{
	   /**
	    * @see javax.faces.webapp.UIComponentTag#getComponentType()
	    */
	   public String getComponentType()
	   {
	      return "eu.cec.digit.circabc.faces.DialogButtons";
	   }

	   /**
	    * @see javax.faces.webapp.UIComponentTag#getRendererType()
	    */
	   public String getRendererType()
	   {
	      return null;
	   }
}
