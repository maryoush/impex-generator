/**
 * 
 */
package de.hybris.platform.impexgen.velocity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{"classpath:test-impex-generator-spring.xml"})
@DirtiesContext
@Ignore
abstract public class BaseFileSandBoxTest
{

	public static final Logger LOG = Logger.getLogger(BaseFileSandBoxTest.class);

	abstract protected Resource getRootDir();

	protected static final String GENERATED_IMPEXES_DIR = "generated";

	/**
	 * @throws IOException
	 */
	protected void cleanUpFileStructure(final boolean recreate) throws IOException
	{
		final File generatedDir = new File(getRootDir().getFile(), GENERATED_IMPEXES_DIR);

		if (generatedDir.exists())
		{
			FileUtils.deleteDirectory(generatedDir);

			if (recreate)
			{
				if (!generatedDir.mkdir())
				{
					LOG.warn("Could not create an empty " + GENERATED_IMPEXES_DIR + " folder");
				}
			}
		}
	}


	protected void assertNothingGenerated(final String rootgenerated) throws IOException
	{
		final File generatedDir = new File(getRootDir().getFile(), rootgenerated);

		Assert.assertFalse("Expect to don't generate dir", generatedDir.exists());

	}

	protected void assertGenerated(final String rootgenerated, final String[] generatedNames) throws IOException
	{
		final File generatedDir = new File(getRootDir().getFile(), rootgenerated);

		final List<File> allGeneratedFiles = new ArrayList(FileUtils.listFiles(generatedDir, new String[]
		{ "impex" }, false));

		Assert.assertNotNull(allGeneratedFiles);
		Assert.assertEquals(generatedNames.length, allGeneratedFiles.size());
		//
		final List<String> generatedNamesList = new ArrayList<String>(Arrays.asList(generatedNames));
		for (final File foundFile : allGeneratedFiles)
		{
			generatedNamesList.remove(foundFile.getName());
		}
		Assert.assertTrue("Should not contain files " + generatedNamesList, generatedNamesList.isEmpty());

	}


	/**
	 * @param lines
	 */
	protected void assertHasLine(final Iterator<String> lines, final String lineExpected)
	{
		Assert.assertTrue("Should have more lines ... ", lines.hasNext());
		Assert.assertEquals(lineExpected, lines.next());
	}

	protected Map<String, List<String>> getAllMatchingTemplates(final File directory)
	{
		final Map<String, List<String>> resultMap = new LinkedHashMap<String, List<String>>();
		for (final File file : FileUtils.listFiles(directory, new String[]
		{ "vt" }, false))
		{
			resultMap.put(directory.getAbsolutePath(), Arrays.asList(file.getName()));
		}
		return resultMap;
	}

	protected Map<String, List<String>> getAllMatchingTemplates(final File directory, final String nameCriteria)
	{
		final Map<String, List<String>> resultMap = new LinkedHashMap<String, List<String>>();
		for (final File file : FileUtils.listFiles(directory, new String[]
		{ "vt" }, false))
		{
			if (file.getName().matches(nameCriteria))
			{
				resultMap.put(directory.getAbsolutePath(), Arrays.asList(file.getName()));
			}
		}
		return resultMap;
	}

	@Before
	public void prepare() throws IOException
	{
		cleanUpFileStructure(true);

	}

	@After
	public void cleanUp() throws IOException
	{
		cleanUpFileStructure(false);
	}

}
