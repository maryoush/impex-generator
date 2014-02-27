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
package de.hybris.platform.impexgen.velocity.resource.store;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.io.Reader;
import java.io.Writer;


/**
 * 
 * 
 */
public interface ScriptReadWriteHandler
{
	public Reader prepareTemplateReader(final ScriptUrlContext urlContext) throws ReadTemplateFileException;

	public void confirmTarget(final ScriptUrlContext urlContext) throws WriteScriptException;

	public Writer prepareTemporaryTargetWriter(final ScriptUrlContext urlContext) throws WriteScriptException;
}
