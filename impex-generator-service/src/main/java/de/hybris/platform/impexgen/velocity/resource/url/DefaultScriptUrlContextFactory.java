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


import de.hybris.platform.impexgen.velocity.resource.name.DefaultPropertyNameBuilder;
import de.hybris.platform.impexgen.velocity.resource.name.DefaultTargetScriptNameBuilder;

import java.io.File;
import java.util.Locale;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultScriptUrlContextFactory implements  ScriptUrlContextFactory
{

	public DefaultScriptUrlContextFactory()
	{
		//
	}

	@Override
	public ScriptUrlContext create(final Locale locale, final File scriptFile, final String propertiesPath, final boolean propertiesPathRelative ,
			final String targetPath, final boolean targetPathRelative)
	{
		final DefaultScriptUrlContext context;
		if (targetPathRelative)
		{
			context = new DefaultScriptUrlContext(locale, scriptFile, propertiesPath,
					targetPath);
		}
		else
		{
			context = new MirroredStructureScriptUrlContext(locale, scriptFile, propertiesPath,
					targetPath);
		}

		context.setPropertyNameBuilder(new DefaultPropertyNameBuilder());
		context.setTargetScriptNameBuilder(new DefaultTargetScriptNameBuilder());
		return context;
	}
}
