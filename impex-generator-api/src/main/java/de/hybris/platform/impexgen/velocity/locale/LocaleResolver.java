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

import java.util.List;
import java.util.Locale;


/**
 * Resolves all available Locales guesses a language iso code for given locale
 */
public interface LocaleResolver
{
	/**
	 * Used for generating localized property 'middle-fix' like products_(en).properties
	 */
	List<Locale> getAllLocales();

	/**
	 * Used to bind a language iso code into impex template
	 */
	String getLanguageCode(Locale loc);

}
