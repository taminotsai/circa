/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.repo.customisation.logo;

import java.io.Serializable;

import org.alfresco.service.cmr.repository.NodeRef;

import eu.cec.digit.circabc.service.customisation.logo.DefaultLogoConfiguration;
import eu.cec.digit.circabc.service.customisation.logo.LogoDefinition;

/**
 * The base logo configuration wrapper
 *
 * @author yanick pignot
 */
public class DefaultLogoConfigurationImpl implements DefaultLogoConfiguration, Serializable, Cloneable
{
	/** */
	private static final long serialVersionUID = -4254883146093804060L;

	private LogoDefinitionImpl logo = null;
	private NodeRef configurationRef;
	private NodeRef configurationOn;

	private boolean logoDisplayedOnMainPage = Boolean.FALSE;
	private int mainPageLogoWidth = Integer.valueOf(-1);
	private int mainPageLogoHeight = Integer.valueOf(-1);
	private boolean mainPageSizeForced = Boolean.FALSE;
	private boolean mainPageLogoAtLeft = Boolean.TRUE;

	private boolean logoDisplayedOnAllPages = Boolean.FALSE;
	private int otherPagesLogoWidth = Integer.valueOf(-1);
	private int otherPagesLogoHeight = Integer.valueOf(-1);
	private boolean otherPagesSizeForced = Boolean.FALSE;


	/**
	 * @param configurationRef
	 */
	/*package*/ DefaultLogoConfigurationImpl(final NodeRef configurationOn, final NodeRef configurationRef)
	{
		super();
		this.configurationRef = configurationRef;
		this.configurationOn = configurationOn;
	}


	/**
	 * @return the logo
	 */
	public final LogoDefinition getLogo()
	{
		return logo;
	}


	/**
	 * @return the logoDisplayedOnAllPages
	 */
	public final boolean isLogoDisplayedOnAllPages()
	{
		return logoDisplayedOnAllPages;
	}

	/**
	 * @return the mainPageLogoAtLeft
	 */
	public final boolean isMainPageLogoAtLeft()
	{
		return mainPageLogoAtLeft;
	}

	/**
	 * @return the mainPageLogoHeight
	 */
	public final int getMainPageLogoHeight()
	{
		return mainPageLogoHeight;
	}

	/**
	 * @return the mainPageLogoWidth
	 */
	public final int getMainPageLogoWidth()
	{
		return mainPageLogoWidth;
	}

	/**
	 * @return the mainPageSizeForced
	 */
	public final boolean isMainPageSizeForced()
	{
		return mainPageSizeForced;
	}

	/**
	 * @return the otherPagesLogoHeight
	 */
	public final int getOtherPagesLogoHeight()
	{
		return otherPagesLogoHeight;
	}

	/**
	 * @return the otherPagesLogoWidth
	 */
	public final int getOtherPagesLogoWidth()
	{
		return otherPagesLogoWidth;
	}

	/**
	 * @return the otherPagesSizeForced
	 */
	public final boolean isOtherPagesSizeForced()
	{
		return otherPagesSizeForced;
	}

	public boolean isLogoDisplayedOnMainPage()
	{
		return logoDisplayedOnMainPage;
	}

	public NodeRef getConfiguredOn()
	{
		return configurationOn;
	}

	@Override
	protected DefaultLogoConfigurationImpl clone() throws CloneNotSupportedException
	{
		final DefaultLogoConfigurationImpl clone = (DefaultLogoConfigurationImpl) super.clone();

		if(this.logo != null)
		{
			clone.logo = new LogoDefinitionImpl();
			clone.logo.setReference(this.logo.getReference());
			clone.logo.setDefinedOn(this.logo.getDefinedOn());
			clone.logo.setDescription(this.logo.getDescription());
			clone.logo.setName(this.logo.getName());
			clone.logo.setTitle(this.logo.getTitle());
		}

		return clone;
	}

	/*package*/ final void setLogo(final NodeRef logoRef, final NodeRef definedOn, final String name, final String title, final String description)
	{
		this.logo = new LogoDefinitionImpl() ;

		this.logo.setReference(logoRef);
		this.logo.setName(name);
		this.logo.setTitle(title);
		this.logo.setDescription(description);
		this.logo.setDefinedOn(definedOn);
	}

	/*package*/ final void setLogo(LogoDefinitionImpl logoDef)
	{
		this.logo = logoDef;
	}

	/*package*/ final void setMainPageLogoConfig(boolean display, int height, int width, boolean sizeForced, boolean logoAtLeft)
	{
		this.logoDisplayedOnMainPage = display;
		this.mainPageLogoAtLeft = logoAtLeft;
		this.mainPageLogoHeight = height;
		this.mainPageLogoWidth = width;
		this.mainPageSizeForced = sizeForced;
	}

	/*package*/ final void setOtherPagesLogoConfig(boolean display, int height, int width, boolean sizeForced)
	{
		this.logoDisplayedOnAllPages = display;
		this.otherPagesLogoHeight = height;
		this.otherPagesLogoWidth = width;
		this.otherPagesSizeForced = sizeForced;
	}

	/*package*/ final void setLogo(final NodeRef logoRef)
	{
		if(logoRef != null)
		{
			if(this.logo == null)
			{
				this.logo = new LogoDefinitionImpl() ;
			}

			this.logo.setReference(logoRef);
		}
	}

	/*package*/ final void setLogo(final String logoRefStr)
	{
		if(notEmpty(logoRefStr))
		{
			setLogo(new NodeRef(logoRefStr));
		}
	}

	/**
	 * @param logoDisplayedOnAllPages the logoDisplayedOnAllPages to set
	 */
	/*package*/ final void setLogoDisplayedOnAllPages(String logoDisplayedOnAllPages)
	{
		if(notEmpty(logoDisplayedOnAllPages))
		{
			this.logoDisplayedOnAllPages = Boolean.valueOf(logoDisplayedOnAllPages);
		}
	}

	/**
	 * @param logoDisplayedOnMainPage the logoDisplayedOnMainPage to set
	 */
	/*package*/ final void setLogoDisplayedOnMainPage(String logoDisplayedOnMainPage)
	{
		if(notEmpty(logoDisplayedOnMainPage))
		{
			this.logoDisplayedOnMainPage = Boolean.valueOf(logoDisplayedOnMainPage);
		}
	}

	/**
	 * @param mainPageLogoAtLeft the mainPageLogoAtLeft to set
	 */
	/*package*/ final void setMainPageLogoAtLeft(String mainPageLogoAtLeft)
	{
		if(notEmpty(mainPageLogoAtLeft))
		{
			this.mainPageLogoAtLeft = Boolean.valueOf(mainPageLogoAtLeft);
		}
	}

	/**
	 * @param mainPageLogoHeight the mainPageLogoHeight to set
	 */
	/*package*/ final void setMainPageLogoHeight(String mainPageLogoHeight)
	{
		if(notEmpty(mainPageLogoHeight))
		{
			this.mainPageLogoHeight = Integer.valueOf(mainPageLogoHeight);
		}
	}

	/**
	 * @param mainPageLogoWidth the mainPageLogoWidth to set
	 */
	/*package*/ final void setMainPageLogoWidth(String mainPageLogoWidth)
	{
		if(notEmpty(mainPageLogoWidth))
		{
			this.mainPageLogoWidth = Integer.valueOf(mainPageLogoWidth);
		}
	}

	/**
	 * @param mainPageSizeForced the mainPageSizeForced to set
	 */
	/*package*/ final void setMainPageSizeForced(String mainPageSizeForced)
	{
		if(notEmpty(mainPageSizeForced))
		{
			this.mainPageSizeForced = Boolean.valueOf(mainPageSizeForced);
		}
	}

	/**
	 * @param otherPagesLogoHeight the otherPagesLogoHeight to set
	 */
	/*package*/ final void setOtherPagesLogoHeight(String otherPagesLogoHeight)
	{
		if(notEmpty(otherPagesLogoHeight))
		{
			this.otherPagesLogoHeight = Integer.valueOf(otherPagesLogoHeight);
		}
	}

	/**
	 * @param otherPagesLogoWidth the otherPagesLogoWidth to set
	 */
	/*package*/ final void setOtherPagesLogoWidth(String otherPagesLogoWidth)
	{
		if(notEmpty(otherPagesLogoWidth))
		{
			this.otherPagesLogoWidth = Integer.valueOf(otherPagesLogoWidth);
		}
	}

	/**
	 * @param otherPagesSizeForced the otherPagesSizeForced to set
	 */
	/*package*/ final void setOtherPagesSizeForced(String otherPagesSizeForced)
	{
		if(notEmpty(otherPagesSizeForced))
		{
			this.otherPagesSizeForced = Boolean.valueOf(otherPagesSizeForced);
		}
	}

	/**
	 * @return the configurationRef
	 */
	/*package*/ final NodeRef getConfigurationRef()
	{
		return configurationRef;
	}

	/**
	 * @return the configurationRef
	 */
	/*package*/ final void setConfigurationRef(final NodeRef nodeRef)
	{
		configurationRef = nodeRef;
	}


	/**
	 * @param logoDisplayedOnAllPages the logoDisplayedOnAllPages to set
	 */
	/*package*/ final void setLogoDisplayedOnAllPages(boolean logoDisplayedOnAllPages)
	{
		this.logoDisplayedOnAllPages = logoDisplayedOnAllPages;
	}

	/**
	 * @param logoDisplayedOnMainPage the logoDisplayedOnMainPage to set
	 */
	/*package*/ final void setLogoDisplayedOnMainPage(boolean logoDisplayedOnMainPage)
	{
		this.logoDisplayedOnMainPage = logoDisplayedOnMainPage;
	}


	/**
	 * @param mainPageLogoAtLeft the mainPageLogoAtLeft to set
	 */
	/*package*/ final void setMainPageLogoAtLeft(boolean mainPageLogoAtLeft)
	{
		this.mainPageLogoAtLeft = mainPageLogoAtLeft;
	}


	/**
	 * @param mainPageLogoHeight the mainPageLogoHeight to set
	 */
	/*package*/ final void setMainPageLogoHeight(int mainPageLogoHeight)
	{
		this.mainPageLogoHeight = mainPageLogoHeight;
	}


	/**
	 * @param mainPageLogoWidth the mainPageLogoWidth to set
	 */
	/*package*/ final void setMainPageLogoWidth(int mainPageLogoWidth)
	{
		this.mainPageLogoWidth = mainPageLogoWidth;
	}


	/**
	 * @param mainPageSizeForced the mainPageSizeForced to set
	 */
	/*package*/ final void setMainPageSizeForced(boolean mainPageSizeForced)
	{
		this.mainPageSizeForced = mainPageSizeForced;
	}


	/**
	 * @param otherPagesLogoHeight the otherPagesLogoHeight to set
	 */
	/*package*/ final void setOtherPagesLogoHeight(int otherPagesLogoHeight)
	{
		this.otherPagesLogoHeight = otherPagesLogoHeight;
	}


	/**
	 * @param otherPagesLogoWidth the otherPagesLogoWidth to set
	 */
	/*package*/ final void setOtherPagesLogoWidth(int otherPagesLogoWidth)
	{
		this.otherPagesLogoWidth = otherPagesLogoWidth;
	}


	/**
	 * @param otherPagesSizeForced the otherPagesSizeForced to set
	 */
	/*package*/ final void setOtherPagesSizeForced(boolean otherPagesSizeForced)
	{
		this.otherPagesSizeForced = otherPagesSizeForced;
	}


	/**
	 * @param configurationRef the configurationRef to set
	 */
	/*package*/ final void setConfigurationRef(final NodeRef configurationOn, final NodeRef configurationRef)
	{
		this.configurationRef = configurationRef;
		this.configurationOn = configurationOn;
	}

	private boolean notEmpty(String str)
	{
		return str != null && str.length() > 0;
	}
}
