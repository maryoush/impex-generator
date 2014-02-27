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

import de.hybris.platform.impexgen.velocity.eval.EvaluatedRow;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.collections.ComparatorUtils;


/**
 * Natural comparator based on the keys
 */
public class DefaultKeyComparator implements Comparator<EvaluatedRow<String, Map<?, ?>>>
{


	@Override
	public int compare(final EvaluatedRow<String, Map<?, ?>> o1, final EvaluatedRow<String, Map<?, ?>> o2)
	{
		return ComparatorUtils.NATURAL_COMPARATOR.compare(o1.getKey(), o2.getKey());
	}

}
