/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.app.context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;


/**
 * Beans supporting the ICircabcContextListener interface are registered against this class. Then Beans
 * which wish to indicate that the UI should refresh itself i.e. dump all cached data and settings,
 * call the UICircabcContextService.igRootChanged() to inform all registered instances of the change.
 * <p>
 * Registered beans will also be informed of changes in location, for example when the current
 * ig root, category, service, ... changes or when the user has changed area.
 *
 * @author yanick pignot
 */
public class UICircabcContextService implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2442813135235907635L;

	/** key for the UI context service in the session */
	private static final String CONTEXT_KEY = "__uiCircabcContextService";

	/** Map of bean registered against the context service */
	private Map<Class, ICircabcContextListener> registeredBeans = new HashMap<Class, ICircabcContextListener>(7, 1.0f);

	private UICircabcContextService()
	{
	}

   /**
    * Returns a Session local instance of the UICircabcContextService
    *
    * @return UIContextService for this Thread
    */
   @SuppressWarnings("unchecked")
   public static UICircabcContextService getInstance(FacesContext fc)
   {
      Map session = fc.getExternalContext().getSessionMap();
      UICircabcContextService service = (UICircabcContextService)session.get(CONTEXT_KEY);
      if (service == null)
      {
         service = new UICircabcContextService();
         session.put(CONTEXT_KEY, service);
      }

      return service;
   }

   /**
    * Register a bean to be informed of context events
    *
    * @param bean    Conforming to the IContextListener interface
    */
   public void registerBean(ICircabcContextListener bean)
   {
      if (bean == null)
      {
         throw new IllegalArgumentException("Bean reference specified cannot be null!");
      }

      this.registeredBeans.put(bean.getClass(), bean);
   }

   /**
    * Remove a bean reference from those notified of changes
    *
    * @param bean    Conforming to the IContextListener interface
    */
   public void unregisterBean(ICircabcContextListener bean)
   {
      if (bean == null)
      {
         throw new IllegalArgumentException("Bean reference specified cannot be null!");
      }

      this.registeredBeans.remove(bean.getClass());
   }

   /**
    * Call to notify all register beans that the user leave circabc
    */
   public void circabcLeaved()
   {
		  for (ICircabcContextListener listener: this.registeredBeans.values())
	      {
	         listener.circabcLeaved();
	      }
   }

   /**
    * Call to notify all register beans that the user enters into circabc
    */
	public void circabcEntered()
    {
		  for (ICircabcContextListener listener: this.registeredBeans.values())
	      {
	         listener.circabcEntered();
	      }
    }


	/**
    * Call to notify all register beans that the user has changef of category header
    */
	public void categoryHeaderChanged()
    {
		  for (ICircabcContextListener listener: this.registeredBeans.values())
	      {
	         listener.categoryHeaderChanged();
	      }
    }


   /**
    * Call to notify all register beans that the user has changed of Category
    */
	public void categoryChanged()
    {
		  for (ICircabcContextListener listener: this.registeredBeans.values())
	      {
	         listener.categoryChanged();
	      }
    }


   /**
    * Call to notify all register beans that the user has change of ig root
    */
	public void igRootChanged()
    {
		  for (ICircabcContextListener listener: this.registeredBeans.values())
	      {
	         listener.igRootChanged();
	      }
    }


	/**
    * Call to notify all register beans that the user
    */
	public void igServiceChanged()
    {
		for (ICircabcContextListener listener: this.registeredBeans.values())
		{
			listener.igServiceChanged();
		}
    }
}
