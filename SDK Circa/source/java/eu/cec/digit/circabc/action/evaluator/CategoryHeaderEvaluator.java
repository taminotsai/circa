package eu.cec.digit.circabc.action.evaluator;

import org.alfresco.web.action.evaluator.BaseActionEvaluator;
import org.alfresco.web.bean.repository.Node;

import eu.cec.digit.circabc.web.bean.navigation.NavigableNodeType;

public class CategoryHeaderEvaluator extends BaseActionEvaluator 
{
	private static final long serialVersionUID = 1L;

	public boolean evaluate(final Node node)
	{
		return (NavigableNodeType.CATEGORY_HEADER.isNodeFromType(node) && !node.getName().equals("CircaBCHeader"));
	}
}
