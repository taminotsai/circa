/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.common.component.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import eu.cec.digit.circabc.service.customisation.nav.NavigationPreference;


/**
 * @author Pignot Yanick
 */
public class UICustomList extends UIRichList {
	// ------------------------------------------------------------------------------
	// Construction

	/**
	 * Default constructor
	 */
	public UICustomList() {
		super("eu.cec.digit.circabc.faces.CustomListRenderer");
	}

	// ------------------------------------------------------------------------------
	// Component implementation

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return "eu.cec.digit.circabc.faces.Data";
	}

	/**
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, state);

		this.configuration = (NavigationPreference) values[values.length - 1];
	}

	/**
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {

		final Object values[] = (Object[]) super.saveState(context);

		final List<Object> list = new ArrayList<Object>(values.length + 1);
		list.addAll(Arrays.asList(values));
		list.add(this.configuration);

		return list.toArray();
	}

	private NavigationPreference configuration = null;

	/**
	 * @return the configuration
	 */
	public final NavigationPreference getConfiguration()
	{
		if (this.configuration == null) {
			ValueBinding vb = getValueBinding("configuration");
			if (vb != null) {
				this.configuration = (NavigationPreference) vb.getValue(getFacesContext());
			}
		}

		return configuration;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public final void setConfiguration(NavigationPreference configuration)
	{
		this.configuration = configuration;
	}

}
