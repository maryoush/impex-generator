/**
 * 
 */
package de.hybris.platform.impexgen.velocity.eval;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;


/**
 * @author mariusz.donigiewicz
 * 
 */
public interface PropertyEvaluatorFactory
{
	PropertyEvaluator create(ScriptUrlContext urlContext);
}
