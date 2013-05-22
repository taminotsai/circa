/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.component.evaluator;

import java.util.List;

import javax.faces.el.ValueBinding;

import org.alfresco.web.ui.common.component.evaluator.BaseEvaluator;

import eu.cec.digit.circabc.web.ui.tag.ListContainsEvaluatorTag;

/**
 * Evaluates to true if the value is contained into the specified list.
 * 
 * @author Matthieu Sprunck
 */
public class ListContainsEvaluator extends BaseEvaluator
{

    @Override
    public boolean evaluate()
    {
        Object value = getValue();
        return getList().contains(value);
    }
    
    /**
     * Get the list to look into
     * 
     * @return a list
     */
    public List getList()
    {
       ValueBinding vb = getValueBinding(ListContainsEvaluatorTag.ATTR_LIST);
       if (vb != null)
       {
          this.list = (List)vb.getValue(getFacesContext());
       }
       return this.list;
    }
    
    /**
     * Set the list to look into
     * 
     * @param list a list
     */
    public void setList(List list)
    {
       this.list = list;
    }
    
    /** The list to look into */
    private List list = null;
}
