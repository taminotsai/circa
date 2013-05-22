/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.wai.dialog.properties;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.web.bean.repository.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.cec.digit.circabc.service.dynamic.property.DynamicProperty;
import eu.cec.digit.circabc.service.dynamic.property.DynamicPropertyService;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;


/**
 *	Bean that backs the "Manage properties" WAI page.
 *
 * @author Slobodan Filipovic
 */
public class ManagePropertiesDialog extends BaseWaiDialog {

	/** */
	private static final long serialVersionUID = -4573272979102874036L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "ManagePropertiesDialog";

	/** The constant for the 'property' parameter */
	public static final String PROPERTY_PARAMETER = "property";

	private static final Log logger = LogFactory.getLog(ManagePropertiesDialog.class);


	private transient DynamicPropertyService propertiesService;

	private List<DynamicProperty> properties;

	@Override
	public void init(Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			if(getActionNode() == null)
			{
				throw new IllegalArgumentException("Node id is a mandatory parameter");
			}

			if(!NavigableNodeType.IG_ROOT.isNodeFromType(getActionNode()))
			{
				throw new IllegalArgumentException("The node id must be an interest group");
			}

			this.properties = this.getPropertiesService().getDynamicProperties(getInterestGroup().getNodeRef());

			if(logger.isDebugEnabled())
			{
				logger.debug(properties.size() + " properties found for the IG " + getInterestGroup().getName()
						+ "\n\tIG NodeRef        :" + getInterestGroup().getNodeRef()
						+ "\n\tDynamic Properties:" + properties
						);
			}

		}
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		// nothing to do
		return null;
	}

	@Override
	public void restored()
	{
		final NodeRef ig = getInterestGroup().getNodeRef();
		this.properties = this.getPropertiesService().getDynamicProperties(ig);
	}

	@Override
	public String getCancelButtonLabel()
	{
		return translate("close");
	}

	public boolean isAddNewAvailable()
	{
		return properties == null || properties.size() < getMaxProperty();
	}

	public int getMaxProperty()
	{
		return DynamicPropertyService.MAX_PROPERTY_BY_IG;
	}

	public String getBrowserTitle()
	{
		return translate("manage_properties_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("manage_properties_dialog_icon_tooltip");
	}

	public Node getInterestGroup()
	{
		return getActionNode();
	}

	/**
	 * @return the properties
	 */
	public List<DynamicProperty> getProperties() {
		return properties;
	}

	/**
	 * @return the propertiesService
	 */
	protected final DynamicPropertyService getPropertiesService()
	{
		if(propertiesService == null)
		{
			propertiesService = Services.getCircabcServiceRegistry(FacesContext.getCurrentInstance()).getDynamicPropertieService();
		}
		return propertiesService;
	}

	/**
	 * @param propertiesService the propertiesService to set
	 */
	public final void setDynamicPropertyService(DynamicPropertyService dynamicPropertyService)
	{
		this.propertiesService = dynamicPropertyService;
	}

}
