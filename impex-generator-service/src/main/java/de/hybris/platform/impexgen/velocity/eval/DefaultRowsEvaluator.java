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

import de.hybris.platform.impexgen.velocity.eval.DefaultPropertyEvaluator.StringEvaluatedRow;
import de.hybris.platform.impexgen.velocity.eval.key.CutOffEdgesKeyEvaluator;
import de.hybris.platform.impexgen.velocity.eval.key.InvalidPropertyKeyFormatException;
import de.hybris.platform.impexgen.velocity.eval.key.KeyEvaluator;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * !!Not thread safe!! factory merging row properties into single item related maps. Merging is based on the key value.
 * 
 * @author mariusz.donigiewicz
 * 
 * @todo extract validation logic to be separated
 * 
 *       <p>
 *       ?? introduce a replace placeholder feature
 *       <p>
 *       ?? use {@link ExtendedProperties} instead of JDK
 * @todo use cascade of {@link RowsEvaluator} for different row / multi-part unique
 */
public class DefaultRowsEvaluator implements RowsEvaluator<StringEvaluatedRow, String>
{
	private static final int TYPE_INDEX = 0;
	private static final int KEY_INDEX = 1;
	private static final int VALUE_INDEX = 2;


	public static final Logger LOG = Logger.getLogger(DefaultRowsEvaluator.class);

	private final Map<String, StringEvaluatedRow> container = new LinkedHashMap<String, StringEvaluatedRow>();

	private KeyEvaluator<String> keyEvaluator = new CutOffEdgesKeyEvaluator();

	private Comparator<StringEvaluatedRow> rowsComparator;

	protected Map<String, StringEvaluatedRow> getContainer()
	{
		return container;
	}

	@Override
	public Set<StringEvaluatedRow> evaluateRows(final Map<String, ?> properties)
	{
		final SortedSet<StringEvaluatedRow> result = new TreeSet<StringEvaluatedRow>(rowsComparator);
		for (final String keyProperty : properties.keySet())
		{
			//override a new evaluated row by removing old existing
			final EvaluatedRow<?, ? extends Map> singleRow = evaluateSingleRow(keyProperty, properties);
			if (result.contains(singleRow))
			{
				result.remove(singleRow);
			}
			result.add((StringEvaluatedRow) singleRow);
		}
		return result;
	}


	@Override
	public Set<StringEvaluatedRow> evaluateRows(final String key, final Map<String, ?> properties)
	{
		final SortedSet<StringEvaluatedRow> result = new TreeSet<StringEvaluatedRow>(rowsComparator);

		for (final String keyProperty : properties.keySet())
		{
			//override a new evaluated row by removing old existing
			final EvaluatedRow<?, ? extends Map> singleRow = evaluateSingleRow(key, keyProperty, properties);
			if (singleRow != null)
			{
				if (result.contains(singleRow))
				{
					result.remove(singleRow);
				}
				result.add((StringEvaluatedRow) singleRow);
			}
		}
		return result;
	}


	protected EvaluatedRow<?, ? extends Map> evaluateSingleRow(final String keyDescriminator, final String keyProperty,
			final Map<String, ?> properties)
	{

		final String[] keyTokens = evaluateKey(keyProperty);
		if (keyTokens[TYPE_INDEX].equalsIgnoreCase(keyDescriminator))
		{
			return mergeEvaluatedRowsIfNeeded(keyProperty, properties, keyTokens);
		}
		else
		{
			return null;
		}
	}

	protected EvaluatedRow<?, ? extends Map> evaluateSingleRow(final String keyProperty, final Map<String, ?> properties)
	{
		final String[] keyTokens = evaluateKey(keyProperty);

		return mergeEvaluatedRowsIfNeeded(keyProperty, properties, keyTokens);
	}

	private EvaluatedRow<?, ? extends Map> mergeEvaluatedRowsIfNeeded(final String keyProperty, final Map<String, ?> properties,
			final String[] keyTokens)
	{
		StringEvaluatedRow resultEntry = null;
		final String key = keyTokens[KEY_INDEX];

		final EvaluatedRow<String, Map<String, String>> existingEntry = getContainer().get(key);
		if (existingEntry == null)
		{
			final Map values = evaluateValues(keyProperty, properties, keyTokens);
			getContainer().put(key, resultEntry = new StringEvaluatedRow(key, values));
			return resultEntry;
		}
		else
		{
			//merge policy here 			
			final Map mergedMap = new LinkedHashMap(existingEntry.getValues());
			mergedMap.putAll(evaluateValues(keyProperty, properties, keyTokens));

			getContainer().put(key, resultEntry = new StringEvaluatedRow(key, Collections.unmodifiableMap(mergedMap)));
			return resultEntry;

		}
	}

	private String[] evaluateKey(final String keyProperty) throws InvalidPropertyKeyFormatException
	{
		final String[] keyTokens = getKeyEvaluator().eval(keyProperty);
		validateEntry(keyTokens);
		if (LOG.isTraceEnabled())
		{
			LOG.trace("Found property " + keyProperty);
		}
		return keyTokens;
	}


	private Map<String, String> evaluateValues(final String keyProperty, final Map<String, ?> properties, final String[] keyTokens)
	{
		return Collections.singletonMap(keyTokens[VALUE_INDEX], (String) properties.get(keyProperty));
	}

	/**
	 * 
	 * TODO validate key entry / add handling for incorrect keys
	 * <p>
	 * ** ignore empty values
	 * </p>
	 * <p>
	 * ** set null empty values
	 * </p>
	 * <p>
	 * ** interrupt on empty values
	 * </p>
	 */
	protected String[] validateEntry(final String[] keyTokens) throws InvalidPropertyKeyFormatException
	{
		if (keyTokens.length != VALUE_INDEX + 1)
		{
			throw new InvalidPropertyKeyFormatException(
					"Inappropriate key tokens found. Expected pattern '(\\w+\\.)(\\w+\\.){1,}(\\w+)' (example product.code.name) but got a ["
							+ print(keyTokens) + "]");
		}
		return keyTokens;
	}

	private String print(final String[] tokens)
	{
		final StringBuilder builder = new StringBuilder();
		for (final String single : tokens)
		{
			builder.append(single);
		}
		return builder.toString();
	}

	@Required
	public void setRowsComparator(final Comparator<StringEvaluatedRow> rowsComparator)
	{
		this.rowsComparator = rowsComparator;
	}


	public void setKeyEvaluator(final KeyEvaluator<String> keyEvaluator)
	{
		this.keyEvaluator = keyEvaluator;
	}

	protected KeyEvaluator<String> getKeyEvaluator()
	{
		return keyEvaluator;
	}



}
