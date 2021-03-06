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


/**
 * @author mariusz.donigiewicz
 * 
 */
public interface FileNameBuilder
{
	String buildFileName(Locale locale, String resourceIdentifier);

}
