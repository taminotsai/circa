/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.ui.tag;

import javax.faces.component.UIComponent;

/**
 * @author Yanick Pignot
 *
 */
public class UploadInputTag extends org.alfresco.web.ui.common.tag.UploadInputTag
{

	public static final String COMPONENT_TYPE = "eu.cec.digit.circabc.faces.UploadInput";

	private String onSubmit;

	@Override
	public String getComponentType()
	{
		return UploadInputTag.COMPONENT_TYPE;
	}

	@Override
    protected void setProperties(UIComponent component)
    {
    	super.setProperties(component);
    	component.getAttributes().put("onSubmit", this.onSubmit);
    }

	@Override
    public void release()
    {
    	super.release();
    	this.onSubmit = null;
    }

    public void setOnSubmit(String onSubmit)
    {
    	this.onSubmit = onSubmit;
    }

}
