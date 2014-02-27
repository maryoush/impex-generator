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
package de.hybris.platform.impexgen.velocity.eval;

import de.hybris.platform.impexgen.velocity.eval.key.InvalidPropertyKeyFormatException;
import de.hybris.platform.impexgen.velocity.resource.PropertyFileMissingHandler;
import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;
import de.hybris.platform.impexgen.velocity.util.LocalizedPropertyUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;


/**
 * 
 * Object available in the velocity context.
 * 
 */
public class DefaultPropertyEvaluator implements PropertyEvaluator
{
	public static final Logger LOG = Logger.getLogger(DefaultPropertyEvaluator.class);

	private final ScriptUrlContext ctx;
	private final PropertyFileMissingHandler missingHandler;

	private RowsEvaluator<? extends EvaluatedRow<?, ?>, ?> rowEvaluator;

	public DefaultPropertyEvaluator(final PropertyFileMissingHandler handler, final ScriptUrlContext ctx)
	{
		this.ctx = ctx;
		this.missingHandler = handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.impexgen.velocity.eval.PropertyEvaluator#load(java.lang.String)
	 */
	@Override
	public Collection<? extends EvaluatedRow<?, ?>> load(final String resourceId)
	{
		return load(resourceId, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.impexgen.velocity.eval.PropertyEvaluator#load(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Collection<? extends EvaluatedRow<?, ?>> load(final String resourceId, final String key)
	{


		final DefaultResourceLoader loader = getDefaultResourceLoader();

		final Resource localizedPropertyResource = loader.getResource("file:" + ctx.getPropertyEffectivePath(resourceId));
		//handle not existing resource
		if (localizedPropertyResource.exists() && localizedPropertyResource.isReadable())
		{
			final Map properties = loadProperties(localizedPropertyResource);
			try
			{
				final RowsEvaluator<? extends EvaluatedRow<?, ?>, ?> rowFactory = getRowEvaluator(properties);
				Set<? extends EvaluatedRow<?, ?>> result = null;
				if (key == null)
				{
					result = rowFactory.evaluateRows(properties);
				}
				else
				{
					result = rowFactory.evaluateRows(key, properties);
				}
				if (LOG.isDebugEnabled())
				{
					for (final EvaluatedRow<?, ?> eachRow : result)
					{
						LOG.debug(eachRow.getKey());
					}
				}
				return result;
			}
			catch (final InvalidPropertyKeyFormatException ike)
			{
				LOG.error("During processing property [" + localizedPropertyResource.getFilename() + "] occured. Details,  "
						+ ike.getMessage());
				throw ike;
			}
			finally
			{
				//deleteResource(res);
			}
		}
		else
		{
			missingHandler.handle(ctx, localizedPropertyResource);
		}

		return Collections.EMPTY_SET;
	}


	protected Map loadProperties(final Resource localizedPropertyResource)
	{
		Map properties = null;
		try
		{
			properties = LocalizedPropertyUtil.loadProperties(localizedPropertyResource);
		}
		catch (final IOException e)
		{

			throw new IllegalStateException(e);
		}
		return properties;
	}


	@Override
	public void setRowEvaluator(final RowsEvaluator<? extends EvaluatedRow<?, ?>, ?> rowEvaluator)
	{
		this.rowEvaluator = rowEvaluator;
	}

	/**
	 * Factory method to create a specific {@link RowsEvaluator}
	 */
	protected RowsEvaluator<? extends EvaluatedRow<?, ?>, ?> getRowEvaluator(@SuppressWarnings("unused") final Map properties)
	{
		return rowEvaluator;
	}


	protected DefaultResourceLoader getDefaultResourceLoader()
	{
		return new FileSystemResourceLoader();
	}

	/**
	 * 
	 * Default {@link String} representation of {@link EvaluatedRow}. Assumes that key is a single value string.
	 */
	public static class StringEvaluatedRow implements EvaluatedRow<String, Map<String, String>>, Comparable<EvaluatedRow>
	{

		private final String key;
		private final Map<String, String> value;

		public StringEvaluatedRow(final String key, final Map<String, String> value)
		{
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey()
		{
			return key;
		}

		@Override
		public Map<String, String> getValues()
		{
			return value;
		}


		@Override
		public String toString()
		{
			return String.format("<<%s>> value [%s]", key, value);
		}

		@Override
		public int hashCode()
		{
			return getKey().hashCode();
		}


		@Override
		public boolean equals(final Object obj)
		{
			if (obj instanceof EvaluatedRow)
			{
				return getKey().equals(((EvaluatedRow) obj).getKey());
			}
			return super.equals(obj);
		}


		@Override
		public int compareTo(final EvaluatedRow given)
		{

			return ComparatorUtils.NATURAL_COMPARATOR.compare(getKey(), given.getKey());
		}

	}


}
