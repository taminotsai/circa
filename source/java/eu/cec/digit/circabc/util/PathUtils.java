/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/
package eu.cec.digit.circabc.util;

import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.repository.Path.Element;
import org.alfresco.util.ISO9075;

/**
 * @author Slobodan Filipovic
 *
 */
public class PathUtils

{

	private static final String HTTP_WWW_ALFRESCO_ORG_MODEL_CONTENT_1_0_CONTAINS = "{http://www.alfresco.org/model/content/1.0}contains";

	private PathUtils()
	{

	}
	public static String getFullPath(Path path, boolean includeFirstSlash)
	{
		return getPath(path, 0,  includeFirstSlash);
	}

	/**
	 *
	 * Get path starting from circabc node
	 *
	 * @param path path of node
	 * @return circabc path of node staring with circabc removing /company_home
	 */
	public static String getCircabcPath(Path path,boolean includeFirstSlash)
	{
		return getPath(path, 2, includeFirstSlash);
	}

	/**
	 *
	 * Get path starting from category node
	 *
	 * @param path path of node
	 * @return circabc path of node staring with circabc removing /company_home/circabc
	 */
	public static String getCategoryPath(Path path,boolean includeFirstSlash)
	{
		return getPath(path, 3, includeFirstSlash);
	}

	/**
	 *
	 * Get path starting from interest group node
	 *
	 * @param path path of node
	 * @return circabc path of node staring with circabc removing /company_home/circabc/categoryxy
	 */
	public static String getInterestGroupPath(Path path,boolean includeFirstSlash)
	{
		return getPath(path, 4,includeFirstSlash);
	}

	/**
	 *
	 * Get path starting from library node
	 *
	 * @param path path of node
	 * @return circabc path of node staring with circabc removing /company_home/circabc/categoryXY/interestGroupXY
	 */
	public static String getLibraryPath(Path path, boolean includeFirstSlash)
	{
		return getPath(path, 5,includeFirstSlash);
	}

	public static String getPath(Path path, int start,boolean includeFirstSlash)
	{


		String result;
		StringBuilder buf = new StringBuilder(256);
		int i = 0;
		for (Element element : path)
		{

			String elementString = element.getElementString();
			if ( (elementString == null) || elementString.equals(HTTP_WWW_ALFRESCO_ORG_MODEL_CONTENT_1_0_CONTAINS))
			{
				continue;
			}
			if (i >= start)
			{
				if (!elementString.equalsIgnoreCase("/"))
				{
					if ( (i == start) && !includeFirstSlash )
					{
						;// do nothing
					}
					else
					{
						buf.append("/");
					}
					int endIndex = elementString.indexOf('}');
					if (endIndex > -1)
					{
						elementString = elementString.substring(endIndex+1);
					}

					buf.append(elementString);
				}
			}
			i++;

		}

		result =  ISO9075.decode(buf.toString());
		return result;
	}
}
