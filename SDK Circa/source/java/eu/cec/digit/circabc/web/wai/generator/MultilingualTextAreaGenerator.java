/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.wai.generator;


import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;

import org.alfresco.web.bean.generator.TextAreaGenerator;
import org.alfresco.web.ui.repo.component.property.PropertySheetItem;
import org.alfresco.web.ui.repo.component.property.UIPropertySheet;

import eu.cec.digit.circabc.web.ui.repo.RepoConstants;

/**
 * WAI Generates a multilingual text area component.
 *
 * @author patrice.coppens@trasys.lu
 * @deprecated this generator can be replaced by TranslateLongTextPropertyGenerator
 */
@Deprecated
public class MultilingualTextAreaGenerator extends TextAreaGenerator
{
	public static final String BEAN_NAME= "CircabcMultilingualTextAreaGenerator";
	   @Override
	   public UIComponent generateAndAdd(FacesContext context, UIPropertySheet propertySheet,
	            PropertySheetItem item)
	   {
	      UIComponent component = super.generateAndAdd(context, propertySheet, item);

	      if ((component instanceof UIInput) && ! (component instanceof UISelectOne))
	      {
	         component.setRendererType(RepoConstants.CIRCABC_FACES_MLTEXTAREA_RENDERER);
	      }
	      else if ((component instanceof UIOutput))
	      {
	         component.setRendererType(RepoConstants.CIRCABC_FACES_MLTEXT_RENDERER);
	      }

	      return component;
	   }
	}