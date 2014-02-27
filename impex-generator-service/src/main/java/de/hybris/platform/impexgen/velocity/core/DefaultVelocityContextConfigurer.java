/**
 * 
 */
package de.hybris.platform.impexgen.velocity.core;

import de.hybris.platform.impexgen.velocity.eval.PropertyEvaluatorFactory;
import de.hybris.platform.impexgen.velocity.locale.LocaleResolver;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Required;



/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultVelocityContextConfigurer implements CoreVelocityConfigurer<VelocityContext, ScriptUrlContext>
{


	//make it configurable	
	private static final String LANG_VARIABLE = "lang";
	private static final String QUERY_VARIABLE = "query";


	private Map properties;
	private PropertyEvaluatorFactory propertyEvaluatorFactory;
	private LocaleResolver localeResolver;

	@Required
	public void setProperties(final Map properties)
	{
		this.properties = properties;
	}

	@Override
	public VelocityContext configure(final ScriptUrlContext urlContext)
	{
		properties.put(QUERY_VARIABLE, propertyEvaluatorFactory.create(urlContext));
		properties.put(LANG_VARIABLE, localeResolver.getLanguageCode(urlContext.getLocale()));
		final VelocityContext executionContext = new VelocityContext(properties);
		return executionContext;
	}


	@Override
	public VelocityContext configure()
	{
		throw new UnsupportedOperationException();
	}

	@Required
	public void setPropertyEvaluatorFactory(final PropertyEvaluatorFactory propertyEvaluatorFactory)
	{
		this.propertyEvaluatorFactory = propertyEvaluatorFactory;
	}

	@Required
	public void setLocaleResolver(final LocaleResolver localeResolverInstance)
	{
		this.localeResolver = localeResolverInstance;
	}


}
