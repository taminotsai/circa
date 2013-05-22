/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.evaluator.GenericEvaluatorTag;

/**
 * Tag to check if a list contains an object.
 *  
 * @author sprunma
 */
public class ListContainsEvaluatorTag extends GenericEvaluatorTag
{
    @Override
    public String getComponentType()
    {
        return "eu.cec.digit.circabc.faces.ListContainsEvaluator";
    }
    
    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
       super.setProperties(component);
       setStringBindingProperty(component, "list", this.list);
    }
    
    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
       super.release();
       this.list = null;
    }
    
    /**
     * Set the list
     *
     * @param list the list value binding
     */
    public void setList(String list)
    {
       this.list = list;
    }

    /** list */
    private String list;
    
    public static final  String ATTR_LIST = "list";
}
