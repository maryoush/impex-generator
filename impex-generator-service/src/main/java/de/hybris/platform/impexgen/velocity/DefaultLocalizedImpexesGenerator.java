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
package de.hybris.platform.impexgen.velocity;

import de.hybris.platform.impexgen.velocity.locale.LocaleResolver;
import de.hybris.platform.impexgen.velocity.core.CoreVelocityConfigurer;
import de.hybris.platform.impexgen.velocity.locale.LocalizedImpexesGenerator;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContextFactory;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.base.Preconditions;



/**
 * @author mariusz.donigiewicz
 * 
 */

public class DefaultLocalizedImpexesGenerator implements LocalizedImpexesGenerator
{
	public static final Logger LOG = Logger.getLogger(DefaultLocalizedImpexesGenerator.class);

	private String sourcePath;
	private boolean sourcePathRelative;
	private String targetPath;
	private boolean targetPathRelative;

	private LocaleResolver localeResolverInstance;
	private ScriptUrlContextFactory scriptUrlContextFactory;
	private Map<String, List<String>> templateFiles;
	private CoreVelocityConfigurer<VelocityContext, ScriptUrlContext> velocityContextConfigurer;

	@Override
	@Required
	public void setLocaleResolverInstance(final LocaleResolver localesResolver)
	{
		//TODO refactor
		this.localeResolverInstance = localesResolver;
	}

	@Required
	public void setScriptUrlContextFactory(final ScriptUrlContextFactory scriptUrlContextFactory)
	{
		this.scriptUrlContextFactory = scriptUrlContextFactory;
	}


	protected CoreVelocityConfigurer<VelocityContext, ScriptUrlContext> getVelocityContextConfigurer()
	{
		return velocityContextConfigurer;
	}

	@Required
	public void setVelocityContextConfigurer(
			final CoreVelocityConfigurer<VelocityContext, ScriptUrlContext> velocityContextConfigurer)
	{
		this.velocityContextConfigurer = velocityContextConfigurer;
	}


	@Override
	public void setSourcePath(final String sourcePath)
	{
		this.sourcePath = sourcePath;
	}

	@Override
	public void setSourcePathRelative(final boolean sourcePathRelative)
	{
		this.sourcePathRelative = sourcePathRelative;
	}

	@Override
	public void setTargetPath(final String targetPath)
	{
		this.targetPath = targetPath;
	}

	@Override
	public void setTargetPathRelative(final boolean targetPathRelative)
	{
		this.targetPathRelative = targetPathRelative;
	}

	@Override
	public void setTemplateFiles(final Map<String, List<String>> templateFiles)
	{
		this.templateFiles = templateFiles;
	}

	@Override
	public void run()
	{
		Preconditions.checkNotNull(sourcePath, "Source path should be not null");
		Preconditions.checkNotNull(targetPath, "Target path should be not null");
		for (final Locale supported : localeResolverInstance.getAllLocales())
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Starting processing for a " + supported);
			}

			processFileSet(supported);

			if (LOG.isDebugEnabled())
			{
				LOG.debug("Finished processing for a " + supported);
			}
		}
	}

	protected void processFileSet(final Locale locale)
	{
		for (final String rootFile : templateFiles.keySet())
		{
			for (final String fileName : templateFiles.get(rootFile))
			{
				final File templateFile = getTemplateFile(new File(rootFile), fileName);
				final ScriptUrlContext urlContext = scriptUrlContextFactory.create(locale, templateFile, sourcePath, sourcePathRelative, targetPath,targetPathRelative); //build script context
				final VelocityExecutor velocityExecutor = getVelocityExecutor();
				final VelocityContext context = getVelocityContextConfigurer().configure(urlContext);
				velocityExecutor.execute(urlContext, context);
			}
		}
	}

	private File getTemplateFile(final File rootDir, final String fileName)
	{
		return new File(rootDir, fileName);
	}

	/**
	 * 
	 * Inject here appropriate {@link VelocityExecutor} instance.
	 */
	public VelocityExecutor getVelocityExecutor()
	{
		throw new UnsupportedOperationException("Override it or inject via lookup in spring");
	}


}
