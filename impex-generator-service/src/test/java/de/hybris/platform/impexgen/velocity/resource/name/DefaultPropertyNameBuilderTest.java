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
package de.hybris.platform.impexgen.velocity.resource.name;

import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.junit.Test;

import junit.framework.Assert;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class DefaultPropertyNameBuilderTest
{
	private final DefaultPropertyNameBuilder nameBuilder = new DefaultPropertyNameBuilder();

	@Test(expected = IllegalArgumentException.class)
	public void testNameForNullLocaleAndNullResource()
	{
		nameBuilder.buildFileName(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNameForOnlyLanguageLocaleAndNullResource()
	{
		nameBuilder.buildFileName(LocaleUtils.toLocale("en"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNameForOnlyLanguageLocaleAndBlankResource()
	{
		nameBuilder.buildFileName(LocaleUtils.toLocale("en"), "     ");
	}

	@Test
	public void testNameForOnlyLanguageLocaleAndResource()
	{
		Assert.assertEquals("some_res_en.properties", nameBuilder.buildFileName(LocaleUtils.toLocale("en"), "some_res"));
	}

	@Test
	public void testNameForFullLocaleAndResource()
	{
		Assert.assertEquals("some_res_en.properties", nameBuilder.buildFileName(LocaleUtils.toLocale("en_UK"), "some_res"));
	}


	//region bayern is not respected
	@Test
	public void testNameForFullLocaleRegionNotSupportedAndResource()
	{
		final Locale loc = LocaleUtils.toLocale("de_DE_by");
		//loc.getVariant();
		Assert.assertEquals("some_res_de.properties", nameBuilder.buildFileName(loc, "some_res"));
	}
}
