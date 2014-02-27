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

import de.hybris.platform.impexgen.velocity.core.CoreVelocityConfigurer;
import de.hybris.platform.impexgen.velocity.eval.key.InvalidPropertyKeyFormatException;
import de.hybris.platform.impexgen.velocity.resource.PropertyResourceNotFoundException;
import de.hybris.platform.impexgen.velocity.resource.store.ReadTemplateFileException;
import de.hybris.platform.impexgen.velocity.resource.store.ScriptReadWriteHandler;
import de.hybris.platform.impexgen.velocity.resource.store.WriteScriptException;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class VelocityExecutor
{
	public static final Logger LOG = Logger.getLogger(VelocityExecutor.class);

	private CoreVelocityConfigurer<VelocityEngine, Void> velocityEngineConfigurer;
	private ScriptReadWriteHandler readWriteHandler;


	public void execute(final ScriptUrlContext urlContext, final VelocityContext velocityContext)
			throws ReadTemplateFileException, WriteScriptException
	{


		execute(urlContext, velocityContext, new VelocityExecutionCallback()
		{

			@Override
			public void onSuccess(final ScriptReadWriteHandler readWritehandler)
			{
				readWritehandler.confirmTarget(urlContext);
			}

			@Override
			public void onError(final ScriptReadWriteHandler readWritehandler, final Throwable exception)
			{
				if (exception instanceof PropertyResourceNotFoundException)
				{
					LOG.warn(exception.getMessage());
				}
				else if (exception instanceof RuntimeException)
				{
					throw (RuntimeException) exception;
				}
				else
				{
					throw new RuntimeException(exception);
				}
			}

		}

		);
	}

	private void execute(final ScriptUrlContext urlContext, final VelocityContext velocityContext,
			final VelocityExecutionCallback callback) throws ReadTemplateFileException, WriteScriptException
	{

		final ScriptReadWriteHandler readWritehandler = getReadWriteHandler();
		try
		{

			if (executeInternal(readWritehandler, urlContext, velocityContext))
			{
				callback.onSuccess(readWritehandler);//any action can be performed only after closing temporary streams
			}
		}
		catch (final MethodInvocationException e)
		{
			callback.onError(readWritehandler, e.getCause());
		}

	}

	private boolean executeInternal(final ScriptReadWriteHandler readWritehandler, final ScriptUrlContext urlContext,
			final VelocityContext velocityContext)
	{
		Writer writer = null;
		Reader reader = null;
		final long start = System.currentTimeMillis();

		//prepareExecutionContext(urlContext, executionContext);
		try
		{
			reader = readWritehandler.prepareTemplateReader(urlContext);
			writer = readWritehandler.prepareTemporaryTargetWriter(urlContext);

			final VelocityEngine engine = getVelocityEngineConfigurer().configure();
			return engine.evaluate(velocityContext, writer, "[" + urlContext.getScriptFileName() + "]", reader);
		}
		catch (final InvalidPropertyKeyFormatException e)
		{
			LOG.error("Error processing property file for locale [" + urlContext.getLocale() + "] in  template ["
					+ buildScriptFilePath(urlContext) + "] for target script [" + urlContext.getTargetScriptEffectivePath() + "]");
			throw e;
		}
		catch (final ParseErrorException e)
		{
			LOG.error("Error parsing template [" + buildScriptFilePath(urlContext) + "] for target script ["
					+ urlContext.getTargetScriptEffectivePath() + "]");
			throw e;
		}
		finally
		{
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(reader);
			if (LOG.isDebugEnabled())
			{
				LOG.debug(" template  [" + urlContext.getScriptFileName() + "] evaluated in " + (System.currentTimeMillis() - start)
						+ " [ms] at :" + urlContext.getScriptRootFolder() + " using [" + urlContext.getLocale() + "] ...");
			}
		}
	}

	@Required
	public void setReadWriteHandler(final ScriptReadWriteHandler readWritehandler)
	{
		this.readWriteHandler = readWritehandler;
	}

	protected ScriptReadWriteHandler getReadWriteHandler()
	{
		return this.readWriteHandler;
	}


	protected CoreVelocityConfigurer<VelocityEngine, Void> getVelocityEngineConfigurer()
	{
		return velocityEngineConfigurer;
	}

	protected String buildScriptFilePath(final ScriptUrlContext urlContext)
	{
		return urlContext.getTargetScriptEffectivePath();
	}

	@Required
	public void setVelocityEngineConfigurer(final CoreVelocityConfigurer<VelocityEngine, Void> velocityEngineConfigurer)
	{
		this.velocityEngineConfigurer = velocityEngineConfigurer;
	}

	interface VelocityExecutionCallback
	{
		void onSuccess(ScriptReadWriteHandler handler);

		void onError(ScriptReadWriteHandler handler, Throwable exception);
	}

}
