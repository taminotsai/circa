/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.component;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Class for setter and getter
 *
 * @author Guillaume
 */
public class UIActionLink extends org.alfresco.web.ui.common.component.UIActionLink
{
	// ------------------------------------------------------------------------------
	// Construction

	/**
	 * Default Constructor
	 */
	public UIActionLink()
	{
		setRendererType("eu.cec.digit.circabc.faces.ActionLinkRenderer");
	}

	// ------------------------------------------------------------------------------
	// Component implementation

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily()
	{
		return "eu.cec.digit.circabc.faces.Controls";
	}

	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(FacesContext context, Object state)
	{
		Object values[] = (Object[]) state;
		// standard component attributes are restored by the super class
		super.restoreState(context, values[0]);
		this.image = (String) values[1];
		this.showLink = (Boolean) values[2];
		this.tooltip = (String) values[3];
		this.target = (String) values[4];
		this.href = (String) values[5];
		this.anchor = (String) values[6];
		this.noDisplay = (Boolean) values[7];
		this.padding = (Integer) values[8];
		this.onclick = (String) values[9];
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(FacesContext context)
	{
		Object values[] = new Object[10];
		// standard component attributes are saved by the super class
		values[0] = super.saveState(context);
		values[1] = this.image;
		values[2] = this.showLink;
		values[3] = this.tooltip;
		values[4] = this.target;
		values[5] = this.href;
		values[6] = this.anchor;
		values[7] = this.noDisplay;
		values[8] = this.padding;
		values[9] = this.onclick;
		return (values);
	}

	// ------------------------------------------------------------------------------
	// Strongly typed component property accessors

	/**
	 * Return the current child parameter map for this action link instance. This map is filled with name/value pairs from any child UIParameter components.
	 *
	 * @return Map of name/value pairs
	 */
	@Override
	public Map<String, String> getParameterMap()
	{
		if (this.params == null)
		{
			this.params = new HashMap<String, String>(1, 1.0f);
		}
		return this.params;
	}

	/**
	 * Get whether to show the link as well as the image if specified
	 *
	 * @return true to show the link as well as the image if specified
	 */
	@Override
	public boolean getShowLink()
	{
		ValueBinding vb = getValueBinding("showLink");
		if (vb != null)
		{
			this.showLink = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.showLink != null)
		{
			return this.showLink.booleanValue();
		} else
		{
			// return default
			return true;
		}
	}

	/**
	 * Set whether to show the link as well as the image if specified
	 *
	 * @param showLink Whether to show the link as well as the image if specified
	 */
	@Override
	public void setShowLink(boolean showLink)
	{
		this.showLink = Boolean.valueOf(showLink);
	}

	/**
	 * Return the Image path to use for this actionlink. If an image is specified, it is shown in additon to the value text unless the 'showLink' property is set to 'false'.
	 *
	 * @return the image path to display
	 */
	@Override
	public String getImage()
	{
		ValueBinding vb = getValueBinding("image");
		if (vb != null)
		{
			this.image = (String) vb.getValue(getFacesContext());
		}

		return this.image;
	}

	/**
	 * Set the Image path to use for this actionlink. If an image is specified, it is shown in additon to the value text unless the 'showLink' property is set to 'false'.
	 *
	 * @param image Image path to display
	 */
	@Override
	public void setImage(String image)
	{
		this.image = image;
	}

	/**
	 * Get the tooltip title text
	 *
	 * @return the tooltip
	 */
	@Override
	public String getTooltip()
	{
		ValueBinding vb = getValueBinding("tooltip");
		if (vb != null)
		{
			this.tooltip = (String) vb.getValue(getFacesContext());
		}

		return this.tooltip;
	}

	/**
	 * Set the tooltip title text
	 *
	 * @param tooltip the tooltip
	 */
	@Override
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}

	/**
	 * Get the target
	 *
	 * @return the target
	 */
	@Override
	public String getTarget()
	{
		ValueBinding vb = getValueBinding("target");
		if (vb != null)
		{
			this.target = (String) vb.getValue(getFacesContext());
		}

		return this.target;
	}

	/**
	 * Set the target
	 *
	 * @param target the target
	 */
	@Override
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
    * Get the padding value for rendering this component in a table.
    *
    * @return the padding in pixels, if set != 0 then a table will be rendering around the items
    */
	@Override
    public int getPadding()
    {
      ValueBinding vb = getValueBinding("padding");
      if (vb != null)
      {
         this.padding = (Integer)vb.getValue(getFacesContext());
      }

      return this.padding != null ? this.padding.intValue() : 0;
    }

	/**
	 * Set the padding value for rendering this component in a table.
	 *
	 * @param padding       value in pixels, if set != 0 then a table will be rendering around the items
	 */
	 @Override
	 public void setPadding(int padding)
	 {
	    this.padding = padding;
	 }

	/**
	 * @return Returns the href.
	 */
	@Override
	public String getHref()
	{
		ValueBinding vb = getValueBinding("href");
		if (vb != null)
		{
			this.href = (String) vb.getValue(getFacesContext());
		}

		return this.href;
	}

	/**
	 * @param href The href to set.
	 */
	@Override
	public void setHref(String href)
	{
		this.href = href;
	}

	/**
	 * Get the anchor
	 *
	 * @return Returns the anchor.
	 */
	public String getAnchor()
	{
		ValueBinding vb = getValueBinding("anchor");
		if (vb != null)
		{
			this.anchor = (String) vb.getValue(getFacesContext());
		}

		return this.anchor;
	}

	/**
	 * Set the anchor
	 *
	 * @param anchor The anchor to set.
	 */
	public void setAnchor(String anchor)
	{
		this.anchor = anchor;
	}

	/**
	 * Get the noDisplay property
	 *
	 * @return the noDisplay
	 */
	public boolean getNoDisplay()
	{
		ValueBinding vb = getValueBinding("noDisplay");
		if (vb != null)
		{
			this.noDisplay = (Boolean) vb.getValue(getFacesContext());
		}

		if (this.noDisplay != null)
		{
			return this.noDisplay.booleanValue();
		} else
		{
			// return default
			return false;
		}
	}

	/**
	 * Set the noDisplay property
	 *
	 * @param noDisplay Whether to output the link
	 */
	public void setNoDisplay(boolean noDisplay)
	{
		this.noDisplay = Boolean.valueOf(noDisplay);
	}

   /**
    * Returns the onclick handler
    *
    * @return The onclick handler
    */
   public String getOnclick()
   {
      ValueBinding vb = getValueBinding("onclick");
      if (vb != null)
      {
         this.onclick = (String)vb.getValue(getFacesContext());
      }

      return this.onclick;
   }

   /**
    * Sets the onclick handler
    *
    * @param onclick The onclick handler
    */
   public void setOnclick(String onclick)
   {
      this.onclick = onclick;
   }

	// ------------------------------------------------------------------------------
	// Private data

	/** True to show the link as well as the image if specified */
	private Boolean showLink = null;

	/** If an image is specified, it is shown in additon to the value text */
	private String image = null;

	/** tooltip title text to display on the action link */
	private String tooltip = null;

	/** the target reference */
	private String target = null;

	/** the anchor reference */
	private String anchor = null;

	/** static href to use instead of an action/actionlistener */
	private String href = null;

	/** The noDisplay property which indicate if html code must be output */
	private Boolean noDisplay = null;

	/** The padding property which indicate the number of blanck car to insert between the image and the link */
	private Integer padding;

	/** the onclick handler */
	private String onclick = null;

	/** Transient map of currently set param name/values pairs */
	private Map<String, String> params = null;
}
