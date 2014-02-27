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
package de.hybris.platform.impexgen.velocity.locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;


/**
 * Sample locales provider
 * 
 */
public class DefaultLocaleResolver implements LocaleResolver
{
	private final List allLocales;


	public DefaultLocaleResolver()
	{
		//
		allLocales = Arrays.asList(Locale.US, Locale.GERMANY);
	}

	public DefaultLocaleResolver(final String supportedCodes)
	{
		allLocales = new ArrayList<Locale>();
		Preconditions.checkArgument(StringUtils.isNotBlank(supportedCodes), "Missing supported locales is codes ");
		for (final String singleCode : Splitter.on(",").split(supportedCodes))
		{
			allLocales.add(LocaleUtils.toLocale(singleCode));
		}
	}

	@Override
	public List<Locale> getAllLocales()
	{
		return allLocales;
	}

	@Override
	public String getLanguageCode(final Locale loc)
	{
		Preconditions.checkNotNull(loc);
		Preconditions.checkNotNull(loc.getLanguage());
		return loc.getLanguage().toLowerCase();
	}

}
