/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.web.ui.repo.converter;


/**
 * The display path of a file link or a folder link must start from the libray
 *
 * @author yanick pignot
 */
public class StaticServiceDisplayPathConverter extends DisplayPathAbstractConverter
{

	/**
	 * <p>The standard converter id for this converter.</p>
	 */
	public static final String CONVERTER_ID = "eu.cec.digit.circabc.faces.StaticServiceDisplayPathConverter";

	public StaticServiceDisplayPathConverter()
	{
		super();
	}

	@Override
	protected FromElement getFromElement()
	{
		return FromElement.SERVICE;
	}

	@Override
	protected LinkType getLinkType()
	{
		return LinkType.NONE;
	}
}
