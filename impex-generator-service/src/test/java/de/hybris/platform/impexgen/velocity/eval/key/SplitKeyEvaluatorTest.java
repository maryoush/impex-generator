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
package de.hybris.platform.impexgen.velocity.eval.key;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class SplitKeyEvaluatorTest
{
	private SplitKeyEvaluator evaluator = null;

	@Before
	public void prepare()
	{
		evaluator = new SplitKeyEvaluator();
		evaluator.setKeySeparator("\\*");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullKey()
	{
		evaluator.eval(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBlankKey()
	{
		evaluator.eval("   \t ");
	}


	@Test
	public void testNoSeparatorPresent()
	{
		assertEqualArrays(new String[]
		{ "a.b.c" }, evaluator.eval("a.b.c"));
	}

	@Test
	public void testSeparatorPresent()
	{
		assertEqualArrays(new String[]
		{ "a", "b.c" }, evaluator.eval("a*b.c"));
	}

	@Test
	public void testSeparatorPresentWithSpace()
	{
		assertEqualArrays(new String[]
		{ "a", "b", "c with d" }, evaluator.eval("a*b*c with d"));
	}

	private void assertEqualArrays(final String[] arrayExp, final String[] result)
	{
		Assert.assertEquals(arrayExp.length, result.length);
		for (int i = 0; i < arrayExp.length; i++)
		{
			Assert.assertEquals(arrayExp[i], result[i]);
		}
	}
}
