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
package de.hybris.platform.impexgen.velocity.ant;

/**
 * 
 * Attribute object for ant task representing templatePath abstraction
 * 
 */
public class TargetPath
{
	private boolean relative = true;

	private String targetFilePath = ".";

	public TargetPath()
	{
		//
	}

	public boolean isRelative()
	{
		return relative;
	}

	public void setRelative(final boolean relative)
	{
		this.relative = relative;
	}

	public String getTargetFilePath()
	{
		return targetFilePath;
	}

	public void setTargetFilePath(final String targetFilePath)
	{
		this.targetFilePath = targetFilePath;
	}
}
