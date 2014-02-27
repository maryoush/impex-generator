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

import de.hybris.platform.impexgen.velocity.eval.DefaultPropertyEvaluator;
import de.hybris.platform.impexgen.velocity.eval.DefaultPropertyEvaluator.StringEvaluatedRow;
import de.hybris.platform.impexgen.velocity.eval.DefaultRowsEvaluator;
import de.hybris.platform.impexgen.velocity.eval.RowsEvaluator;
import de.hybris.platform.impexgen.velocity.eval.key.InvalidPropertyKeyFormatException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class RenderedRowFactoryTest
{
	private RowsEvaluator factory;

	private final Map allProperties = new HashMap();

	private final Map<String, StringEvaluatedRow> resultMap = new LinkedHashMap<String, DefaultPropertyEvaluator.StringEvaluatedRow>();

	@Before
	public void prepare()
	{
		factory = new DefaultRowsEvaluator()
		{
			@Override
			protected Map<String, StringEvaluatedRow> getContainer()
			{
				return resultMap;
			}
		};

	}

	@Test
	public void testAddForEmptyContiner()
	{
		allProperties.put("type.key.attribute1", "value1");

		factory.evaluateRows(allProperties);

		Assert.assertEquals(1, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row = resultMap.get("key");
		Assert.assertEquals("key", row.getKey());

		Assert.assertEquals(1, row.getValues().size());
		Assert.assertEquals("value1", row.getValues().get("attribute1"));
	}

	@Test
	public void testAddForEmptyContinerWithDescriminatorNotApplying()
	{
		allProperties.put("type.key.attribute1", "value1");

		factory.evaluateRows("other", allProperties);

		Assert.assertEquals(0, resultMap.size());
	}

	@Test
	public void testAddForEmptyContinerWithDescriminatorApplying()
	{
		allProperties.put("type.key.attribute1", "value1");

		factory.evaluateRows("type", allProperties);

		Assert.assertEquals(1, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row = resultMap.get("key");
		Assert.assertEquals("key", row.getKey());

		Assert.assertEquals(1, row.getValues().size());
		Assert.assertEquals("value1", row.getValues().get("attribute1"));
	}


	@Test
	public void testAddForNonEmptyContiner()
	{
		allProperties.put("type.key.attribute1", "value1");
		allProperties.put("type.key.attribute2", "value2");

		allProperties.put("type.keyOther.attribute2", "value3");

		factory.evaluateRows(allProperties);

		Assert.assertEquals(2, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row1 = resultMap.get("key");
		Assert.assertEquals("key", row1.getKey());

		Assert.assertEquals(2, row1.getValues().size());
		Assert.assertEquals("value1", row1.getValues().get("attribute1"));
		Assert.assertEquals("value2", row1.getValues().get("attribute2"));

		final StringEvaluatedRow row2 = resultMap.get("keyOther");
		Assert.assertEquals("keyOther", row2.getKey());

		Assert.assertEquals(1, row2.getValues().size());
		Assert.assertEquals("value3", row2.getValues().get("attribute2"));


	}

	@Test
	public void testAddForNonEmptyContinerWithDescriminatorApplying()
	{
		allProperties.put("type.key.attribute1", "value1");
		allProperties.put("type.key.attribute2", "value2");

		allProperties.put("type.keyOther.attribute2", "value3");

		factory.evaluateRows("type", allProperties);

		Assert.assertEquals(2, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row1 = resultMap.get("key");
		Assert.assertEquals("key", row1.getKey());

		Assert.assertEquals(2, row1.getValues().size());
		Assert.assertEquals("value1", row1.getValues().get("attribute1"));
		Assert.assertEquals("value2", row1.getValues().get("attribute2"));

		final StringEvaluatedRow row2 = resultMap.get("keyOther");
		Assert.assertEquals("keyOther", row2.getKey());

		Assert.assertEquals(1, row2.getValues().size());
		Assert.assertEquals("value3", row2.getValues().get("attribute2"));


	}

	@Test
	public void testAddForNonEmptyContinerWithDescriminatorNotApplying()
	{
		allProperties.put("type.key.attribute1", "value1");
		allProperties.put("type.key.attribute2", "value2");

		allProperties.put("type.keyOther.attribute2", "value3");

		factory.evaluateRows("notExisting", allProperties);

		Assert.assertEquals(0, resultMap.size());

	}


	@Test
	public void testMergeExisting()
	{
		allProperties.put("type.key.attribute1", "value1");
		allProperties.put("type.key.attribute2", "value2");

		factory.evaluateRows(allProperties);

		Assert.assertEquals(1, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row = resultMap.get("key");
		Assert.assertEquals("key", row.getKey());

		Assert.assertEquals(2, row.getValues().size());
		Assert.assertEquals("value1", row.getValues().get("attribute1"));
		Assert.assertEquals("value2", row.getValues().get("attribute2"));

	}


	@Test
	public void testMergeExistingWithDesciminatorNotApplying()
	{
		allProperties.put("type1.key.attribute1", "value1");
		allProperties.put("type1.key.attribute2", "value2");

		allProperties.put("type2.key.attribute3", "value3");

		allProperties.put("type3.key.attribute4", "value4");

		factory.evaluateRows("notExisting", allProperties);

		Assert.assertEquals(0, resultMap.size());

	}


	@Test(expected = InvalidPropertyKeyFormatException.class)
	public void testInvalidKeyAComment()
	{
		allProperties.put("#", "value1");

		factory.evaluateRows("notExisting", allProperties);
	}

	@Test(expected = InvalidPropertyKeyFormatException.class)
	public void testInvalidKeyACommentWithAddon()
	{
		allProperties.put("#.something", "value1");

		factory.evaluateRows("notExisting", allProperties);
	}

	@Test
	public void testMergeExistingWithDesciminatorApplying()
	{
		allProperties.put("type1.key.attribute1", "value1");
		allProperties.put("type1.key.attribute2", "value2");

		allProperties.put("type2.key.attribute3", "value3");

		allProperties.put("type3.key.attribute4", "value4");

		factory.evaluateRows("type1", allProperties);

		Assert.assertEquals(1, resultMap.size());
		Assert.assertNotNull(resultMap.get("key"));

		final StringEvaluatedRow row = resultMap.get("key");
		Assert.assertEquals("key", row.getKey());

		Assert.assertEquals(2, row.getValues().size());
		Assert.assertEquals("value1", row.getValues().get("attribute1"));
		Assert.assertEquals("value2", row.getValues().get("attribute2"));

	}

	/**
	 * to do order of generated properties
	 */
	@Ignore
	@Test
	public void testKeyOrder()
	{
		allProperties.put("type1.xXx.attribute3", "value3");

		allProperties.put("type1.Aaa.attribute1", "value1");
		allProperties.put("type1.Aaa.attribute2", "value2");

		allProperties.put("type1.bbb.attribute3", "value3");

		allProperties.put("type1.zzz.attribute4", "value4");

		allProperties.put("type1.CC134.attribute3", "value3");

		factory.evaluateRows("type1", allProperties);

		Assert.assertEquals(5, resultMap.size());
		Assert.assertNotNull(resultMap.get("Aaa"));
		Assert.assertNotNull(resultMap.get("bbb"));
		Assert.assertNotNull(resultMap.get("zzz"));

		final Iterator<String> keys = resultMap.keySet().iterator();
		final String first = keys.next();
		System.err.println(first);
		final String second = keys.next();
		System.err.println(second);
		final String third = keys.next();
		System.err.println(third);
		final String fourth = keys.next();
		System.err.println(fourth);
		final String fifth = keys.next();
		System.err.println(fifth);
	}
}
