/**
 * 
 */
package de.hybris.platform.impexgen.velocity.eval;

import de.hybris.platform.impexgen.velocity.resource.PropertyFileMissingHandler;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import org.springframework.beans.factory.annotation.Required;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultPropertyEvaluatorFactory implements PropertyEvaluatorFactory
{
	private PropertyFileMissingHandler propertyFileMissingHandler;

	@Required
	public void setPropertyFileMissingHandler(final PropertyFileMissingHandler propertyFileMissingHandler)
	{
		this.propertyFileMissingHandler = propertyFileMissingHandler;
	}

	public RowsEvaluator getRowsEvaluator()
	{
		throw new UnsupportedOperationException("Inject a RowsEvaluator implementation");
	}


	@Override
	public PropertyEvaluator create(final ScriptUrlContext urlContext)
	{
		final PropertyEvaluator propertyEvaluator = new DefaultPropertyEvaluator(propertyFileMissingHandler, urlContext);
		propertyEvaluator.setRowEvaluator(getRowsEvaluator());
		return propertyEvaluator;
	}
}
