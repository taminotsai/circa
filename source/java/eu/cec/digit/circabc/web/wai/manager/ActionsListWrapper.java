/*--+
 |     Copyright European Community 2006 - Licensed under the EUPL V.1.0
 |
 |          http://ec.europa.eu/idabc/en/document/6523
 |
 +--*/

package eu.cec.digit.circabc.web.wai.manager;

import java.io.Serializable;

import org.alfresco.web.bean.repository.Node;


/**
 * Wrap a simple Action list for any container for the right menu.
 *
 * @author yanick pignot
 */
public class ActionsListWrapper implements Serializable
{

	private static final long serialVersionUID = -3257773143591523891L;


	private Object context;
	private String actions;

	/**
	 * @param context
	 * @param actions
	 */
	public ActionsListWrapper(Node context, String actions)
	{
		super();
		this.context = context;
		this.actions = actions;
	}

	/**
	 * @param context
	 * @param actions
	 */
	public ActionsListWrapper(Object context, String actions)
	{
		super();
		this.context = context;
		this.actions = actions;
	}

	/**
	 * @return the actions
	 */
	public String getActions()
	{
		return actions;
	}

	/**
	 * @return the context
	 */
	public Object getContext()
	{
		return context;
	}



}
