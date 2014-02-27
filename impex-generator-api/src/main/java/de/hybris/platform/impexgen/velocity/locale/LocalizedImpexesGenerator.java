/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2014 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package de.hybris.platform.impexgen.velocity.locale;

import java.util.List;
import java.util.Map;

public interface LocalizedImpexesGenerator extends  Runnable
{
	void setSourcePath(String sourcePath);

	void setSourcePathRelative(boolean sourcePathRelative);

	void setTargetPath(String targetPath);

	void setTargetPathRelative(boolean targetPathRelative);

	void setLocaleResolverInstance(final LocaleResolver localesResolvers);

	void setTemplateFiles(final Map<String, List<String>> templateFiles);
}
