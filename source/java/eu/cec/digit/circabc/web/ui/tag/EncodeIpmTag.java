/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.tag;

import javax.faces.component.UIComponent;

import org.alfresco.web.ui.common.tag.HtmlComponentTag;

/**
 * Display a survey of IPM to encode.
 * 
 * @author Matthieu Sprunck
 */
public class EncodeIpmTag extends HtmlComponentTag
{

    //  ------------------------------------------------------------------------------
    // Component methods 
    
    /**
     * @see javax.faces.webapp.UIComponentTag#getComponentType()
     */
    public String getComponentType()
    {
        return "eu.cec.digit.circabc.faces.EncodeIpm";
    }

    /**
     * @see javax.faces.webapp.UIComponentTag#getRendererType()
     */
    public String getRendererType()
    {
        return null;
    }
    
    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
       super.release();
       this.value = null;
       this.survey = null;
       this.lang = null;
    }
    
    /**
     * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component)
    {
       super.setProperties(component);
       setStringProperty(component, ATTR_VALUE, this.value);
       setStringProperty(component, ATTR_SURVEY, this.survey);
       setStringProperty(component, ATTR_LANG, this.lang);
    }
    
    //  ------------------------------------------------------------------------------
    // Bean implementation 
    
    /**
     * Setter method for survey
     * @param param the param to set
     */
    public void setSurvey(String survey)
    {
        this.survey = survey;
    }

    /**
     * Setter method for value
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }
    
    /**
     * Setter method for lang
     * @param lang the lang to set
     */
    public void setLang(String lang)
    {
        this.lang = lang;
    }
    
    //  ------------------------------------------------------------------------------
    // Data
    
    public static final String ATTR_VALUE = "value";
    public static final String ATTR_SURVEY = "survey";
    public static final String ATTR_LANG = "lang";
    
    /** The value */
    private String value;
    
    /** The survey's short name */
    private String survey;
    
    /** The selected lang  */
    private String lang;
}
