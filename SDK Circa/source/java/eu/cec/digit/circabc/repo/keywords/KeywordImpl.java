/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.keywords;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.repository.MLText;
import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.keyword.Keyword;

/**
 * This class represents a Keyword. A collection of keywords can be defined on a
 * node.
 *
 * @author Yanick Pignot
 */
public class KeywordImpl implements Keyword, Serializable
{
    /** */
	private static final long serialVersionUID = -6691678274689941982L;

	/** The value of the keyword */
    private String value;

    /** The ml values of the keyword */
    private MLText mlValues;

    /** The id of the keyword */
    private NodeRef id;

    /**
     * Constructor for a non multilingual keyword
     *
     * @param value
     *            The value of the keyword
     *
     */
    /*package*/ KeywordImpl(final NodeRef id, final String value)
    {
        this.id = id;
        this.value = value;
        mlValues = null;
    }

    /**
     * Constructor for a multilingual keyword but withouth translation
     *
     * @param value
     *            The value of the keyword
     * @param locale
     *            The keyword's locale
     */
    /*package*/ KeywordImpl(final NodeRef id, final Locale locale, final String value)
    {
        this.id = id;
        if(locale == null)
        {
            this.value = value;
            this.mlValues = null;
        }
        else
        {
            this.mlValues = new MLText();
            this.mlValues.put(locale, value);
            this.value = null;
        }
    }

    /**
     * Constructor
     *
     * @param value
     *            The value of the keyword
     * @param locale
     *            The keyword's locale
     */
    /*package*/ KeywordImpl(final NodeRef id, final MLText mlValues)
    {
        this.id = id;
        this.mlValues = mlValues;
        this.value = null;
    }

    /**
     * Constructor for a non multilingual keyword
     *
     * @param value
     *            The value of the keyword
     *
     */
    public KeywordImpl(final String value)
    {
        this(null, null, value);
    }

    /**
     * Constructor for a multilingual keyword but withouth translation
     *
     * @param value
     *            The value of the keyword
     * @param locale
     *            The keyword's locale
     */
    public KeywordImpl(final Locale locale, final String value)
    {
        this(null, locale, value);
    }

    /**
     * Constructor
     *
     * @param value
     *            The value of the keyword
     * @param locale
     *            The keyword's locale
     */
    public KeywordImpl(final MLText mlValues)
    {
        this(null, mlValues);
    }

    /**
     * Getter for value file
     *
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @return the mlValues
     */
    public MLText getMLValues()
    {
        return mlValues;
    }

    public boolean isKeywordTranslated()
    {
        return mlValues != null;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((mlValues == null) ? 0 : mlValues.hashCode());
		result = PRIME * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final KeywordImpl other = (KeywordImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mlValues == null) {
			if (other.mlValues != null)
				return false;
		} else if (!mlValues.equals(other.mlValues))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

    /**
     * @return
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        final StringBuffer buff = new StringBuffer();

        if(isKeywordTranslated())
        {
            boolean first = true;

            for(Map.Entry<Locale, String> entry : getMLValues().entrySet())
            {
                if(first)
                {
                    first = false;
                }
                else
                {
                    buff.append(", ");
                }

                buff
                    .append('(')
                    .append(entry.getKey().getLanguage())
                    .append(") ")
                    .append(entry.getValue());
            }
        }
        else
        {
            buff.append(getValue());
        }

        return 	buff.toString();
    }


    public String getString()
    {
        return toString();
    }

    /**
     * @return the id
     */
    public NodeRef getId()
    {
        return id;
    }

    public void addTranlatation(final Locale locale, final String keyword) throws UnsupportedOperationException
    {
        if(locale == null)
        {
            throw new NullPointerException("The locale is a mandatory parameter");
        }
        if(keyword == null || keyword.length() < 1)
        {
            throw new NullPointerException("The keyword is a mandatory parameter");
        }
        if(!isKeywordTranslated())
        {
            throw new UnsupportedOperationException("Make the keyword multilingual before add transtlation");
        }

        mlValues.addValue(locale, keyword);

    }

    public void makeMultilingual(final Locale locale) throws UnsupportedOperationException
    {
        if(locale == null)
        {
            throw new NullPointerException("The locale is a mandatory parameter");
        }
        if(isKeywordTranslated())
        {
            throw new UnsupportedOperationException("The keyword is already seted being multilingual");
        }

        this.mlValues = new MLText();
        this.mlValues.put(locale, value);
        this.value = null;

    }

    public void setTranlatations(final MLText translations)
    {
        if(translations == null || translations.size() < 1)
        {
            throw new NullPointerException("At least one translation is required");
        }

        this.mlValues = translations;
        this.value = null;

    }

	@Override
	public boolean exists(Locale locale, String value) {
		boolean result = false ;
		if (this.mlValues != null){
			String localValue = this.mlValues.getValue(locale);
			if (localValue != null )
			{
				result  = localValue.equals(value);
			}
		}
		
		return result; 
	}

}
