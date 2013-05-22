/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;


/**
 * The display path of a file link or a folder link must start from circabc root
 *
 * @author yanick pignot
 */
public class CircabcDisplayPathConverter extends DisplayPathAbstractConverter
{
	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.CircabcDisplayPathConverter";


	public CircabcDisplayPathConverter()
	{
		super();
	}

	@Override
	protected FromElement getFromElement()
	{
		return FromElement.CIRCABC_ROOT;
	}

	@Override
	protected LinkType getLinkType()
	{
		return LinkType.DOWNLOAD_BROWSE;
	}
}
