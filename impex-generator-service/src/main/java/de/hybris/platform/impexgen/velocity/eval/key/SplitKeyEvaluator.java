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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;

import com.google.common.base.Preconditions;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class SplitKeyEvaluator implements KeyEvaluator<String>
{

	public String DEFAULT_KEY_SEPARATOR = ".";

	private String separator = DEFAULT_KEY_SEPARATOR;

	@Override
	public String[] eval(final String key)
	{
		Preconditions.checkArgument(StringUtils.isNotBlank(key), "Given key should be not blank");
		return key.split(separator);
	}

	protected String getSeparator()
	{
		return separator;
	}


	@Required
	@Override
	public void setKeySeparator(final String separator)
	{
		this.separator = separator;
	}

}
