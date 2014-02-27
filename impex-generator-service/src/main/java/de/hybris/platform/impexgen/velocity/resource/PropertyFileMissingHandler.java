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
package de.hybris.platform.impexgen.velocity.resource;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import org.springframework.core.io.Resource;


/**
 * @author mariusz.donigiewicz
 * 
 */
public interface PropertyFileMissingHandler
{
	void handle(ScriptUrlContext ctx, Resource resource);
}
