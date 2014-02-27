/**
 * 
 */
package de.hybris.platform.impexgen.velocity.core;

import de.hybris.platform.impexgen.velocity.eval.PropertyEvaluator;
import de.hybris.platform.impexgen.velocity.eval.PropertyEvaluatorFactory;
import de.hybris.platform.impexgen.velocity.locale.LocaleResolver;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.InOrderImpl;



/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultVelocityContextConfigurerTest
{
	@InjectMocks
	private final CoreVelocityConfigurer<VelocityContext, ScriptUrlContext> configurer = new DefaultVelocityContextConfigurer();

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private ScriptUrlContext context;
	@Mock
	private PropertyEvaluatorFactory propertyEvaluatorFactory;
	@Mock
	private LocaleResolver localeResolver;
	@Mock
	private Map config;
	@Mock
	private PropertyEvaluator propertyEvaluator;

	private final Locale locale = Locale.FRENCH;


	private InOrder orderMock;

	@Before
	public void preppare()
	{
		MockitoAnnotations.initMocks(this);
		orderMock = new InOrderImpl(Arrays.asList(config, propertyEvaluatorFactory, localeResolver, context));
	}


	@Test(expected = UnsupportedOperationException.class)
	public void testNotImplementedMethod()
	{
		configurer.configure();
	}

	@Test
	public void testConfigure()
	{
		BDDMockito.given(propertyEvaluatorFactory.create(context)).willReturn(propertyEvaluator);
		BDDMockito.given(context.getLocale()).willReturn(locale);
		BDDMockito.given(localeResolver.getLanguageCode(locale)).willReturn("LANG");

		configurer.configure(context);

		orderMock.verify(propertyEvaluatorFactory).create(context);
		orderMock.verify(config).put("query", propertyEvaluator);
		orderMock.verify(localeResolver).getLanguageCode(context.getLocale());
		orderMock.verify(config).put("lang", "LANG");
	}
}
