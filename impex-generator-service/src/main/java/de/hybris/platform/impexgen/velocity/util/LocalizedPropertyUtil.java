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
package de.hybris.platform.impexgen.velocity.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.log4j.Logger;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;


/**
 * Ant UTF-8 based reader util for loading properties.
 */
abstract public class LocalizedPropertyUtil
{
	public static final Logger LOG = Logger.getLogger(LocalizedPropertyUtil.class);


	public static Properties loadProperties(final Resource resource) throws IOException
	{
		final Properties props = new Properties();
		fillProperties(props, resource);
		return props;
	}

	/**
	 * Fill the given properties from the given resource.
	 * 
	 * @param props
	 *           the Properties instance to fill
	 * @param resource
	 *           the resource to load from
	 * @throws IOException
	 *            if loading failed
	 */
	public static void fillProperties(final Properties props, final Resource resource) throws IOException
	{
		final Reader is = new InputStreamReader(new BOMInputStream(resource.getInputStream()), "UTF-8");
		try
		{
			props.load(is);
		}
		catch (final IOException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(e);
			}
			throw new ResourceNotFoundException(e);
		}
		catch (final IllegalArgumentException ile)//case when not supported encoding given
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug(ile);
			}
			throw new ParseErrorException(ile.getMessage());
		}
		finally
		{
			IOUtils.closeQuietly(is);
		}
	}
}
