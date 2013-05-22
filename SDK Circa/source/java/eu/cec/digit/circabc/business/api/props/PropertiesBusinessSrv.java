/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.business.api.props;

import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Business service manage properties.
 *
 * <p><i>This service must keep the focus on hiding the background implementation</i></p>
 *
 * @author Yanick Pignot
 */
public interface PropertiesBusinessSrv
{	
	/**
	 * Compute a valid file name replacing unsuported caraters by -
	 *
	 * @param name					The filname (not null)
	 * @return						A valid file name
	 */
	public String computeValidName(final String name);

	/**
	 * Compute a valid an unique child filename for a given parent
	 *
	 * @param parent								An existing parent
	 * @param name									A filename name (not null)
	 * @return
	 */
	public String computeValidUniqueName(final NodeRef parent, final String name);

	/**
	 * Compute a user friendly language description of the locale
	 * 
	 * @param locale					The locale to translate
	 * @return							The translation of the language of the locale
	 */
	public String computeLanguageTranslation(final Locale locale);
	
	/**
	 * Return the properties of a node keyed by their names.
	 *
	 * @param nodeRef							An existing nodeRef
	 * @return									The list of the node properties
	 */
	public Map<String, PropertyItem> getProperties(final NodeRef nodeRef);

}
