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
package de.hybris.platform.impexgen.velocity.util.standalone;

import java.io.IOException;


/**
 * Simple util to generate a property file raw format from impex file
 */
public class ImpexToTemplateUtil extends ImpexToPropertyUtil
{

	final private static String WHOLE_SCRIPT_PROPERTY = "cms-content";

	static class TemplateProcessor extends PropertyProcessor
	{

		public TemplateProcessor(final String path) throws IOException
		{
			super(path);
		}


		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.hybris.platform.impexgen.velocity.util.ImpexToPropertyUtil.PropertyProcessor#processLines(java
		 * .lang.String)
		 */
		@Override
		protected void createHeader(final String single)
		{
			super.createHeader(single);
			final String typeCode = header.getType();
			System.out.println(String.format("#set ( $%s = $query.load('" + WHOLE_SCRIPT_PROPERTY + "', '%s') )", typeCode
					.toLowerCase().trim(), typeCode.trim()));
			//System.out.println(String.format("# %s", typeCode));
			System.out.println(header.getWholeDefinition());
			System.out.println(String.format("#foreach( $row in $%s )", typeCode.toLowerCase().trim()));
			System.out.print(";$row.key");
			for (final String column : header.getColumns())
			{
				System.out.print(String.format(";$row.values.%s", extractProperty(column)));
			}
			System.out.println();
			System.out.println("#end");
			System.out.println();
			super.processLines(single);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.hybris.platform.impexgen.velocity.util.ImpexToPropertyUtil.PropertyProcessor#printLine(java.lang
		 * .String, java.lang.String, java.lang.String)
		 */
		@Override
		protected void printLine(final String key, final String nextToken, final String property)
		{
			//
		}
	}

	public static void main(final String[] args) throws IOException
	{
		new TemplateProcessor(args[0]);
	}






}
