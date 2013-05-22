/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.service.dynamic.property;

/**
 * @author Yanick Pignot
 */
public enum DynamicPropertyType
{
	DATE_FIELD
	{
		public String getModelDataDefinition()
		{
			return this.name();
		}
	},

	TEXT_FIELD
	{
		public String getModelDataDefinition()
		{
			return this.name();
		}
	},

	TEXT_AREA
	{
		public String getModelDataDefinition()
		{
			return this.name();
		}
	},

	SELECTION
	{
		public String getModelDataDefinition()
		{
			return this.name();
		}
	};

	abstract public String getModelDataDefinition();

}
