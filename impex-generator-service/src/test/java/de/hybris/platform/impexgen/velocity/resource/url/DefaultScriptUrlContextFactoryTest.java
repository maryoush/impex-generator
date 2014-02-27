/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2012 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package de.hybris.platform.impexgen.velocity.resource.url;

import java.io.File;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


/**
 * 
 */
public class DefaultScriptUrlContextFactoryTest
{
	@Mock
	private File scriptFile;

	private final  String sourcePath = "foo";
	private final String targetPath = "bar";

	private final Locale locale = Locale.CANADA;

	private final DefaultScriptUrlContextFactory factory = new DefaultScriptUrlContextFactory();


	@Before
	public void prepare()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFactoryWhenTargetRelative()
	{
		final ScriptUrlContext ctx = factory.create(locale, scriptFile, sourcePath,true, targetPath,true);

		Assert.assertTrue(ctx instanceof DefaultScriptUrlContext);
	}


	@Test
	public void testFactoryWhenTargetAbsolute()
	{

		final ScriptUrlContext ctx = factory.create(locale, scriptFile, sourcePath, true,  targetPath, false);

		Assert.assertTrue(ctx instanceof MirroredStructureScriptUrlContext);
	}
}
