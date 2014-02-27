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

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;


/**
 * 
 * Basic property name builder
 * 
 */
public class DefaultPropertyNameBuilder implements FileNameBuilder
{

	private static final String PROPERTIES_EXTENSION = ".properties";

	@Override
	public String buildFileName(final Locale locale, final String resourceIdentifier)
	{
		Preconditions.checkArgument(StringUtils.isNotBlank(resourceIdentifier), "Given resource should be not blank");
		Preconditions.checkNotNull(locale, "Given locale should be not null");

		return (resourceIdentifier + "_" + locale.getLanguage() + PROPERTIES_EXTENSION);

	}
}
