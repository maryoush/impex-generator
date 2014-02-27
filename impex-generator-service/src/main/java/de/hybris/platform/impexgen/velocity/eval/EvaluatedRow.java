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


/**
 * Abstraction containing evaluated data where the key is to be unique (it might contain various kind of data) for
 * target impex script and value is {@link Map} of type attribute and it value.
 * 
 * @param <KEY>
 * @param <VALUE>
 */
public interface EvaluatedRow<KEY, VALUE extends Map>
{

	abstract public KEY getKey();

	abstract public VALUE getValues();
}
