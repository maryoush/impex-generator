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
package de.hybris.platform.impexgen.velocity.resource.store;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultScriptReadWriteHandler implements ScriptReadWriteHandler
{
	public static final Logger LOG = Logger.getLogger(DefaultScriptReadWriteHandler.class);

	/**
	 * Prepares a {@link Reader} for a input velocity template.
	 */
	@Override
	public Reader prepareTemplateReader(final ScriptUrlContext urlContext) throws ReadTemplateFileException
	{
		final File velocityScriptPath = new File(urlContext.getScriptRootFolder(), urlContext.getScriptFileName());
		try
		{
			final InputStreamReader reader = new InputStreamReader(new FileInputStream(velocityScriptPath));
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Created reader for velocity template impex  " + velocityScriptPath);
			}
			return reader;
		}
		catch (final FileNotFoundException fnfe)
		{
			throw new ReadTemplateFileException(fnfe);
		}
	}

	/**
	 * Copies a temporary impex script into its final destination {@link ScriptUrlContext#getTargetScriptEffectivePath()}
	 * and deletes a temporary source afterwards.
	 */
	@Override
	public void confirmTarget(final ScriptUrlContext urlContext) throws WriteScriptException
	{
		try
		{
			FileUtils.copyFile(urlContext.getTargetScriptTemporaryFile(), new File(urlContext.getTargetScriptEffectivePath()));

		}
		catch (final IOException e)
		{
			throw new WriteScriptException(e);
		}
		finally
		{
			FileUtils.deleteQuietly(urlContext.getTargetScriptTemporaryFile());
		}

	}

	/**
	 * Prepares a {@link Writer} for output script.
	 */
	@Override
	public Writer prepareTemporaryTargetWriter(final ScriptUrlContext urlContext) throws WriteScriptException
	{
		try
		{
			final String templateBaseName = FilenameUtils.getBaseName(urlContext.getScriptFileName());
			if (LOG.isDebugEnabled())
			{
				LOG.debug("Creating temporory writer for template file " + templateBaseName);
			}
			return createWriterInternal(urlContext.getTargetScriptTemporaryFile());
		}
		catch (final IOException e)
		{
			throw new WriteScriptException(e);
		}
	}


	protected Writer createWriterInternal(final File targetfile) throws IOException
	{
		if (!targetfile.canWrite())
		{
			LOG.debug("Can not write " + targetfile.getAbsolutePath() + " trying to create it ....");
			final File targetScriptFolder = targetfile.getParentFile();
			if (!targetScriptFolder.exists())
			{
				FileUtils.forceMkdir(targetScriptFolder);
				if (!targetScriptFolder.exists())
				{
					throw new WriteScriptException("Can not create parent dir " + targetfile.getParentFile() + " aborting ");
				}
			}
			else
			{
				LOG.debug("success.");
			}
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Created writer for generated impex  " + targetfile.getAbsolutePath());
		}
		return new FileWriterWithEncoding(targetfile, "UTF-8");
	}



}
