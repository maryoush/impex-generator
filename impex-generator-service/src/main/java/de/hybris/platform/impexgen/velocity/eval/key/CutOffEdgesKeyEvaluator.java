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

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Preconditions;



/**
 * @author mariusz.donigiewicz
 * 
 */
public class CutOffEdgesKeyEvaluator extends SplitKeyEvaluator
{


	@Override
	public String[] eval(final String key)
	{
		Preconditions.checkArgument(StringUtils.isNotBlank(key), "Given key should be not blank");

		final StringTokenizer tokenizerFromStart = new StringTokenizer(key, getSeparator(), true);

		String firstBunch = null;
		String lastBunch = null;
		if (tokenizerFromStart.countTokens() > 1 && tokenizerFromStart.hasMoreTokens()) //first
		{
			firstBunch = tokenizerFromStart.nextToken();
		}
		else
		{
			return new String[]
			{ key };
		}

		final StringTokenizer tokenizerFromEnd = new StringTokenizer(StringUtils.reverse(key), getSeparator(), true);

		if (tokenizerFromEnd.countTokens() > 1 && tokenizerFromEnd.hasMoreTokens()) //first
		{
			lastBunch = StringUtils.reverse(tokenizerFromEnd.nextToken());
		}
		else
		{
			return new String[]
			{ firstBunch, StringUtils.stripStart(key, firstBunch) };
		}

		final String middle = StringUtils.stripEnd(StringUtils.stripStart(key, firstBunch), lastBunch);

		final String middleStripped = StringUtils.stripEnd(StringUtils.stripStart(middle, getSeparator()), getSeparator());

		if (StringUtils.isNotBlank(middleStripped))
		{
			return new String[]
			{ firstBunch, middleStripped, lastBunch };
		}
		else
		{
			return new String[]
			{ firstBunch, lastBunch };
		}
	}


}
