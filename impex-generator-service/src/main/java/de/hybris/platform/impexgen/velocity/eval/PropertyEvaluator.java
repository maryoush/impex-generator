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

import java.util.Collection;



/**
 * @author mariusz.donigiewicz
 * 
 */
public interface PropertyEvaluator
{
	/**
	 * 
	 * Method which loads a given resource. The resource is a identifier used to build a physical path to property file
	 * containing localized attribute values. Method returns collection of evaluated rows. See {@link EvaluatedRow} for
	 * more details.
	 * 
	 */
	Collection<? extends EvaluatedRow<?, ?>> load(final String resourceId);

	/**
	 * 
	 * Method which loads a given resource. The resource is a identifier used to build a physical path to property file
	 * containing localized attribute values. Adds additional key for filtering out localized properties not matching
	 * given type.
	 * 
	 * Method returns collection of evaluated rows. See {@link EvaluatedRow} for more details.
	 * 
	 */
	Collection<? extends EvaluatedRow<?, ?>> load(final String resourceId, final String key);

	void setRowEvaluator(final RowsEvaluator<? extends EvaluatedRow<?, ?>, ?> rowEvaluator);

}