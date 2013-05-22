/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.keyword;

import java.util.Locale;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * This class represents a Keyword. A collection of keywords can be defined on a
 * node. A keyword can be either multilingual or not.
 *
 * @author Matthieu Sprunck
 * @author Yanick Pignot
 */
public interface Keyword
{

    /**
     * Getter for value file
     *
     * @return the value
     */
    public String getValue();

    /**
     * @return the mlValues
     */
    public MLText getMLValues();

    /**
	 * @return the node reference where the keyword is stored
	 */
    public NodeRef getId();

    /**
     * @return true if the keyword is setted as being multilingual
     */
    public boolean isKeywordTranslated();


    /**
     * Used to get the toString method within a JSF compoment.
     *
     * @return the toString method
     */
    public String getString();

	/**
	 * Add a translation to the keyword
	 *
	 * @param locale		the locale of the new translation
	 * @param keyword		the translated value
	 * @throws UnsupportedOperationException		error when the keyword <b>is not set</b> multilingual yet
	 */
	public void addTranlatation(Locale locale, String keyword) throws UnsupportedOperationException;

	/**
	 * Set the translations of to the keyword by erasing the previous ones
	 *
	 * @param translations		the locale of the new translation
	 */
	public void setTranlatations(MLText translations);

	/**
	 * Make the current NON MULTILINGUAL value to the keyword.
	 *
	 * @param locale
	 * @throws UnsupportedOperationException		error when the keyword <b>is set</b> multilingual yet
	 */
	public void makeMultilingual(Locale locale)throws UnsupportedOperationException;
	
	/**
	 * Check if Keyword contains string value in given local 
	 * @param locale
	 * @param value
	 * @return
	 */
	boolean exists(Locale locale, String value);
	

}
