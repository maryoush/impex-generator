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

import de.hybris.platform.impexgen.velocity.api.LocaleResolverFactory;
import de.hybris.platform.impexgen.velocity.locale.LocaleResolver;
import de.hybris.platform.impexgen.velocity.locale.LocalizedImpexesGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.base.Preconditions;




/**
 * @author mariusz.donigiewicz
 * 
 */
public class GenerateLocalizedImpexesTask extends Task
{
	private static final String DEFAULT_SPRING_CONFIGURATION_PATH = "classpath:META-INF/spring/impex-generator-spring.xml";

	//private static final String TEMPLATE_HEADER_PROPERTY = "impex.generation.template.header";
	private static final String PROPERTIES_SUPPORTED_LOCALES = "impex.generation.supported.locales";

	private LocalizedImpexesGenerator impexGeneratorBean;
	private LocaleResolverFactory localeResolverFactory;
	private String springContextPath = DEFAULT_SPRING_CONFIGURATION_PATH;

	private SourcePath sourcePath;
	private TargetPath targetPath;


	public SourcePath createSourcePath()
	{
		sourcePath = new SourcePath(new ArrayList<FileSet>());
		return sourcePath;
	}

	public void addSourcePath(final SourcePath sourcePath)
	{
		this.sourcePath = sourcePath;
	}

	public TargetPath createTargetPath()
	{
		targetPath = new TargetPath();
		return targetPath;
	}

	public void addTargetPath(final TargetPath targetPath)
	{
		this.targetPath = targetPath;
	}

	public void addSpringContextPath(final String springContextPath)
	{
		this.springContextPath = springContextPath;
	}

	protected Map<String,String> getSystemProperties()
	{
		return getProject().getProperties();
	}

	protected Map<String, List<String>> createMatchingTemplates()
	{
		final Map<String, List<String>> resultMap = new LinkedHashMap<String, List<String>>();
		for (final FileSet singleFileSet : sourcePath.getTemplateFileSet())
		{
			final File rootDir = singleFileSet.getDir();
			final String[] fileNames = singleFileSet.getDirectoryScanner(getProject()).getIncludedFiles();
			resultMap.put(rootDir.getAbsolutePath(), Arrays.asList(fileNames));
		}
		return resultMap;
	}


	@Override
	public void init() throws BuildException
	{
		final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		applicationContext.setClassLoader(this.getClass().getClassLoader());
		applicationContext.setConfigLocation(DEFAULT_SPRING_CONFIGURATION_PATH);
		applicationContext.refresh();

		Preconditions.checkNotNull(applicationContext);
		Preconditions.checkNotNull(impexGeneratorBean = applicationContext.getBean(LocalizedImpexesGenerator.class),
				"Given task bean 'LocalizedImpexesGenerator' should be not null");
		Preconditions.checkNotNull(localeResolverFactory = applicationContext.getBean(LocaleResolverFactory.class),
						"Given task bean 'LocaleResolverFactory' should be not null");

		super.init();
	}

	@Override
	public void execute() throws BuildException
	{
		final LocaleResolver localeResolver =localeResolverFactory.create(getSystemProperties().get(PROPERTIES_SUPPORTED_LOCALES));

		impexGeneratorBean.setLocaleResolverInstance(localeResolver);

		impexGeneratorBean.setSourcePath(sourcePath.getPropertyFilePath());
		impexGeneratorBean.setSourcePathRelative(sourcePath.isRelative());
		impexGeneratorBean.setTargetPath(targetPath.getTargetFilePath());
		impexGeneratorBean.setTargetPathRelative(targetPath.isRelative());

		impexGeneratorBean.setTemplateFiles(createMatchingTemplates());

		impexGeneratorBean.run();
	}




}
