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

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.types.FileSet;


/**
 * 
 * Attribute object for ant task representing templatePath abstraction
 * 
 */
public class SourcePath
{
	private List<FileSet> templatesFileSet = new ArrayList<FileSet>();

	private static final String CURRENT_DIR = ".";

	private String propertyFilePath = CURRENT_DIR;

	private boolean relative = true;

	public SourcePath(final List<FileSet> templatesFileSet)
	{
		this.templatesFileSet = templatesFileSet;
	}


	public List<FileSet> getTemplateFileSet()
	{
		return templatesFileSet;
	}


	public void addFileset(final FileSet templateFileSet)
	{
		this.templatesFileSet.add(templateFileSet);
	}

	public void setRelative(final boolean relative)
	{
		this.relative = relative;
	}

	public boolean isRelative()
	{
		return relative;
	}

	public String getPropertyFilePath()
	{
		return propertyFilePath;
	}


	public void setPropertyFilePath(final String propertyFilePath)
	{
		this.propertyFilePath = propertyFilePath;
	}

}
