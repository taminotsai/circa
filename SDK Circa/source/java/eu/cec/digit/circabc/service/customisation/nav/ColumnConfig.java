/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.customisation.nav;

/**
 * The base representation of a column navigation customisation.
 *
 * @author Yanick Pignot
 */
public interface ColumnConfig
{
	/**
	 * @return the converter
	 */
	public abstract String getConverter();

	/**
	 * @return the label
	 */
	public abstract String getLabel();
	
	
	/**
	 * @param value
	 */
	public abstract void setLabel(String value);

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @return the resolver
	 */
	public abstract String getResolver();
	
	
	/**
	 * @return true if column is dynamic property otherwise false
	 */
	public boolean isDynamicProperty();

}