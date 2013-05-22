/*--+
    |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
    |
    |          http://ec.europa.eu/idabc/en/document/6523
    |
    +--*/
package eu.cec.digit.circabc.web.wai.dialog.ml;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.alfresco.repo.node.MLPropertyInterceptor;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.ml.ContentFilterLanguagesService;
import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.web.ui.common.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import eu.cec.digit.circabc.web.Beans;
import eu.cec.digit.circabc.web.Services;
import eu.cec.digit.circabc.web.app.CircabcNavigationHandler;
import eu.cec.digit.circabc.web.wai.dialog.BaseWaiDialog;
import eu.cec.digit.circabc.web.wai.dialog.generic.LightDescriptionSizeExceedException;
import eu.cec.digit.circabc.web.wai.generator.BaseDialogLauncherGenerator;
import eu.cec.digit.circabc.web.wai.generator.TranslateLongTextPropertyGenerator;
import eu.cec.digit.circabc.web.wai.generator.TranslateLongTextPropertyGeneratorNoRichText;

/**
 * Benas that back the translate propety dialog.
 *
 * @author yanick pignot
 *
 */
public class TranslatePropertyDialog extends BaseWaiDialog{

	private static final long serialVersionUID = -7111360183999853548L;

	/** Public JSF Bean name */
	public static final String BEAN_NAME = "TranslatePropertyDialog";

    public static final String MSG_ERROR_LOCALE_REQUIRED = "add_new_keyword_dialog_error_locale_required";
    public static final String MSG_ERROR_VALUE_REQUIRED = "add_new_keyword_dialog_error_value_required";
    public static final String MSG_ERROR_LOCALE_DUPLICATED = "add_new_keyword_dialog_error_locale_duplicated";

	public static final String WAI_DIALOG_CALL = CircabcNavigationHandler.WAI_DIALOG_PREFIX + "translatePropertyDialogWai";

	private transient ContentFilterLanguagesService contentFilterLanguagesService;

	private String propertyId;
	private QName  propertyQname;
	private String i18nPropety;
	private boolean displayAsArea;
	private boolean displayRichText;

	/** Logger (coppepa: logger must be final) */
    private static final Log logger = LogFactory.getLog(TranslatePropertyDialog.class);

    /** datamodel for table of translations for users */
    private transient DataModel translationDataModel = null;
    private List<MLPropertyWrapper> propertyTranslation = null;

    private String value;
    private String language;

	@Override
	public void init(final Map<String, String> parameters)
	{
		super.init(parameters);

		if(parameters != null)
		{
			value = null;
            language = null;
            propertyTranslation = new ArrayList<MLPropertyWrapper>(30);
            translationDataModel = null;
            i18nPropety = null;
            displayAsArea = false;
            displayRichText = true;
			propertyId = parameters.get(BaseDialogLauncherGenerator.PARAM_PROPERTY_KEY);
		}

		if(getActionNode() == null)
		{
			throw new IllegalArgumentException("Node id is a mandatory parameter");
		}

		if(propertyId == null || propertyId.trim().length() == 0)
		{
			throw new IllegalArgumentException("The property id is a mandatory parameter");
		}

		int idx = propertyId.indexOf(':');

		if(idx < 0)
		{
			propertyQname = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, propertyId);
		}
		else
		{
			final String uri = getNamespaceService().getNamespaceURI(propertyId.substring(0, idx));
			propertyQname = QName.createQName(uri, propertyId.substring(idx + 1));
		}

		final PropertyDefinition propertyDef = getDictionaryService().getProperty(propertyQname);

		if (propertyDef == null || !propertyDef.getDataType().getName().equals(DataTypeDefinition.MLTEXT))
		{
			throw new IllegalArgumentException("The property type definition must be a MLText !!");
		}

		final MLText values = getMLPropertyValue(getActionNode().getNodeRef(), this.propertyQname);
		if(values != null)
		{
			for(Map.Entry<Locale, String> translation : values.entrySet())
			{
				propertyTranslation.add(new MLPropertyWrapper(translation.getValue(), translation.getKey()));
			}
		}

		i18nPropety = translate(propertyQname.getLocalName());
		displayAsArea = isTextArea(parameters);
		displayRichText = isRichTextArea(parameters);
	}

	@Override
	protected String finishImpl(FacesContext context, String outcome) throws Exception
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("Trying to add translation to the property " + propertyId + " of node " + getActionNode().getName() + "...");
        }

        final MLText mlText = new MLText();
        //	set the translations
        for (final MLPropertyWrapper wrapper : propertyTranslation)
        {
            mlText.addValue(wrapper.getLocale(), wrapper.getValue());
        }

       final boolean wasMLAware = MLPropertyInterceptor.isMLAware();

    	try
        {
            MLPropertyInterceptor.setMLAware(true);
            getNodeService().setProperty(getActionNode().getNodeRef(), this.propertyQname, mlText);
        }
        finally
        {
            MLPropertyInterceptor.setMLAware(wasMLAware);
        }


    	if(logger.isDebugEnabled())
    	{
	            logger.debug("Property " + propertyId + "  successfully updated for the node " + getActionNode().getName()
	                    + "\n	node: " + getActionNode().getNodeRef()
	                    + "\n	prop: " + propertyQname
	                    + "\n	values: " + mlText);
	    }


		//	refresh the edit content properties dialog
		Beans.getEditNodePropertiesDialog().setPropetyDefinedOutside(propertyQname);

		return outcome;
	}

    public void addSelection(ActionEvent event)
    {
        final UIInput inputText = (UIInput) event.getComponent().findComponent("translate-property-value-input");
        final UIInput inputArea = (UIInput) event.getComponent().findComponent("translate-property-value-area");
        final UISelectOne select = (UISelectOne) event.getComponent().findComponent("translate-property-language");

        final String selLang = (String) select.getValue();
        final String selValue = inputText != null ? ((String) inputText.getValue()).trim() : ((String) inputArea.getValue()).trim();

        boolean error = false;

        if(selValue.length() < 1)
        {
        	Utils.addErrorMessage(translate(MSG_ERROR_VALUE_REQUIRED));
        	error = true;
        }

        if(selLang.length() < 1)
        {
        	Utils.addErrorMessage(translate(MSG_ERROR_LOCALE_REQUIRED));
        	error = true;
        }

        for(MLPropertyWrapper wrapper : propertyTranslation)
        {
        	if(selLang.equalsIgnoreCase(wrapper.getLanguage()))
            {
            	Utils.addErrorMessage(translate(MSG_ERROR_LOCALE_DUPLICATED));
            	error = true;
            	break;
            }
        }

        String finalValue = selValue;
    	
    	if(!displayRichText)
    	{
    		finalValue = Jsoup.parse(selValue).text();
    		
    		if(finalValue.length()>LightDescriptionSizeExceedException.MAX_CHARACTER_LIMIT)
      	  	{
      		  Utils.addErrorMessage(translate("lightDescription_limit_exceed"));
      		  error=true;
      	  	}
    	}
        
        if(!error)
        {

        	propertyTranslation.add(new MLPropertyWrapper(finalValue, new Locale(selLang)));
        	this.value = null;
        }
    }

    /**
     * Method calls by the dialog to getr the available langages.
     *
     * @return the list of languages where at least one property define
     */
    public SelectItem[] getLanguages()
    {
        // get the list of filter languages
        final List<String> languages = getContentFilterLanguagesService().getFilterLanguages();

        final List<String> filteredLanguages = new ArrayList<String>(languages.size());
        filteredLanguages.addAll(languages);

        for(final MLPropertyWrapper wrapper : propertyTranslation)
        {
            if(wrapper.getLocale() != null)
            {
                filteredLanguages.remove(wrapper.getLocale().getLanguage());
            }
            else
            {
                logger.warn("The MLProperty wrapper " + wrapper + " has not language defined.");
                throw new IllegalStateException("The MLProperty wrapper " + wrapper + " has not language defined. ");
            }
        }

        // set the item selection list
        final SelectItem[] items = new SelectItem[filteredLanguages.size()];
        int idx = 0;

        for (final String lang : filteredLanguages)
        {
            final String label = getContentFilterLanguagesService().getLabelByCode(lang);
           items[idx] = new SelectItem(lang, label);
           idx++;
        }

        return items;
    }

    /**
     * Returns the properties for current property translations JSF DataModel
     *
     * @return JSF DataModel representing the translation of the property
     */
    public DataModel getTranslationDataModel()
    {
        if (this.translationDataModel == null)
        {
            this.translationDataModel = new ListDataModel();
        }

        this.translationDataModel.setWrappedData(this.propertyTranslation);

        return this.translationDataModel;
    }

    /**
     * Action handler called when the Remove button is pressed to remove a property translation
     */
    public void removeSelection(ActionEvent event)
    {
        MLPropertyWrapper wrapper = (MLPropertyWrapper) this.translationDataModel.getRowData();
        if (wrapper != null)
        {
            this.propertyTranslation.remove(wrapper);
        }
    }


    /**
     * @return the contentFilterLanguagesService
     */
    protected final ContentFilterLanguagesService getContentFilterLanguagesService()
    {
        if(contentFilterLanguagesService == null)
        {
            contentFilterLanguagesService = Services.getAlfrescoServiceRegistry(FacesContext.getCurrentInstance()).getContentFilterLanguagesService();
        }
        return contentFilterLanguagesService;
    }

    /**
     * @param contentFilterLanguagesService the contentFilterLanguagesService to set
     */
    public final void setContentFilterLanguagesService(ContentFilterLanguagesService contentFilterLanguagesService)
    {
        this.contentFilterLanguagesService = contentFilterLanguagesService;
    }

	public String getBrowserTitle()
	{
		return translate("translate_property_dialog_browser_title");
	}

	public String getPageIconAltText()
	{
		return translate("translate_property_dialog_icon_tooltip");
	}

	@Override
	public String getContainerDescription()
	{
		return translate("translate_property_dialog_description", i18nPropety, getActionNode().getName());
	}

	@Override
	public String getContainerTitle()
	{
		return translate("translate_property_dialog_title", i18nPropety);
	}
	 /**
     * @return the language
     */
    public String getLanguage()
    {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language)
    {
        this.language = language;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String val)
    {
    	if(val!=null && !(val.length() == 0 ))
    	{
	    	Whitelist basicWhitelist = new Whitelist();
	    	basicWhitelist.addTags("p", "span", "strong", "b", "i", "u", "br", "sub", "sup", "a");
	    	basicWhitelist.addAttributes(":all", "style");
	    	this.value = Jsoup.clean(val, basicWhitelist);
    	}
    	else
    	{
    		this.value = val;
    	}
    }

    /**
	 * @return the displayAsArea
	 */
	public final boolean isDisplayAsArea()
	{
		return displayAsArea;
	}

	/**
	 * @param displayAsArea the displayAsArea to set
	 */
	public final void setDisplayAsArea(boolean displayAsArea)
	{
		this.displayAsArea = displayAsArea;
	}

    private MLText getMLPropertyValue(final NodeRef nodeRef, final QName propertyQname)
    {
    	MLText properties = null;

    	final boolean wasMLAware = MLPropertyInterceptor.isMLAware();

    	try
        {
            MLPropertyInterceptor.setMLAware(true);
            properties = (MLText) getNodeService().getProperty(nodeRef, propertyQname);
        }
        finally
        {
            MLPropertyInterceptor.setMLAware(wasMLAware);
        }

        return properties;
    }

    private boolean isTextArea(final Map<String, String> parameters)
    {
    	if(parameters == null)
    	{
    		return false;
    	}
    	else
    	{
    		final String area = parameters.get(TranslateLongTextPropertyGenerator.PARAM_PROPERTY_TEXT_AREA);

    		if(area == null)
    		{
    			return false;
    		}
    		else
    		{
    			return Boolean.parseBoolean(area);
    		}
    	}
    }
    
    private boolean isRichTextArea(Map<String, String> parameters) {
    	
    	if(parameters == null)
    	{
    		return true;
    	}
    	else
    	{
    		final String area = parameters.get(TranslateLongTextPropertyGeneratorNoRichText.PARAM_PROPERTY_USE_RICH_TEXT);

    		if(area == null)
    		{
    			return true;
    		}
    		else
    		{
    			return Boolean.parseBoolean(area);
    		}
    	}
	}

	/**
	 * @return the displayRichText
	 */
	public boolean isDisplayRichText() {
		return displayRichText;
	}

	/**
	 * @param displayRichText the displayRichText to set
	 */
	public void setDisplayRichText(boolean displayRichText) {
		this.displayRichText = displayRichText;
	}
}
