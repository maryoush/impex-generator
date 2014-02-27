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

import java.util.Map;
import java.util.Set;



public interface RowsEvaluator<TYPE extends EvaluatedRow<?, ? extends Map>, KEY>
{

	Set<TYPE> evaluateRows(String keyDescriminator, Map<KEY, ?> allProperties);

	Set<TYPE> evaluateRows(Map<KEY, ?> allProperties);
}
