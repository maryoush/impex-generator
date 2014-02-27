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
package de.hybris.platform.impexgen.velocity.handler;

import org.apache.log4j.Logger;
import org.apache.velocity.app.event.InvalidReferenceEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;


public class LogMissingAttributeInvalidReferenceEventHandler implements InvalidReferenceEventHandler
{

	private static final Logger LOG = Logger.getLogger("CONSOLE");


	@Override
	public Object invalidGetMethod(final Context context, final String reference, final Object object, final String property,
			final Info info)
	{

		LOG.info("<<Property '" + property + "' getter for object " + object + " '" + reference
				+ "' not found or inaccessinble at " + info.getLine() + "," + info.getColumn() + " in " + info.getTemplateName()
				+ ">>");
		return "";
	}

	@Override
	public boolean invalidSetMethod(final Context context, final String leftreference, final String rightreference, final Info info)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object invalidMethod(final Context context, final String reference, final Object object, final String method,
			final Info info)
	{

		LOG.info("<<Method " + method + " for object " + object + " '" + reference + "' not found or inaccessinble at "
				+ info.getLine() + "," + info.getColumn() + " in " + info.getTemplateName() + ">>");
		return "";
	}

}
