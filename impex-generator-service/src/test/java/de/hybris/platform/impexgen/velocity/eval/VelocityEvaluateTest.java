/**
 * 
 */
package de.hybris.platform.impexgen.velocity.eval;

import de.hybris.platform.impexgen.velocity.BaseFileSandBoxTest;
import de.hybris.platform.impexgen.velocity.DefaultLocalizedImpexesGenerator;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author mariusz.donigiewicz
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
{"classpath:test-impex-generator-spring.xml"})
public class VelocityEvaluateTest extends BaseFileSandBoxTest
{
	@javax.annotation.Resource
	private DefaultLocalizedImpexesGenerator impexGenerator;

	@Value("classpath:test/test-encoding")
	private Resource rootDir;

	@Value("classpath:test/test-encoding/key-data-lexcial-error-chars.test")
	private Resource resourceLexical;

	private static final String GENERATED_IMPEXES_DIR = "generated";

	@Override
	protected Resource getRootDir()
	{
		return rootDir;
	}


	@Test
	public void testLoadLexicalErrorData() throws Exception
	{

		final String target  = "./" + GENERATED_IMPEXES_DIR;
		final boolean targetRelative = true;

		final String  source  = ".";

		final File file = resourceLexical.getFile().getAbsoluteFile();
		final Map<String, List<String>> templates = Collections.singletonMap(file.getParentFile().getAbsolutePath(),
				Collections.singletonList(file.getName()));

		impexGenerator.setTemplateFiles(templates);
		impexGenerator.setSourcePath(source);
		impexGenerator.setTargetPath(target);
		impexGenerator.setTargetPathRelative(targetRelative);
		//impexGenerator.setPropertyEvaluatorFactory(removeRowEvaluator);


		impexGenerator.run();

		assertGenerated(GENERATED_IMPEXES_DIR, new String[]
		{ "key-data-lexcial-error-chars_de.impex", "key-data-lexcial-error-chars_en.impex" });
		assertFileContains(GENERATED_IMPEXES_DIR, new String[]
		{ "key-data-lexcial-error-chars_de.impex", "key-data-lexcial-error-chars_en.impex" },
				"INSERT_UPDATE Media;$contentCV[unique=true];");
		assertFileContains(GENERATED_IMPEXES_DIR, new String[]
		{ "key-data-lexcial-error-chars_en.impex" }, "INSERT_UPDATE Media;$contentCV[lang=en];");

		assertFileContains(GENERATED_IMPEXES_DIR, new String[]
		{ "key-data-lexcial-error-chars_de.impex" }, "INSERT_UPDATE Media;$contentCV[lang=de];");
		assertFileContains(GENERATED_IMPEXES_DIR, new String[]
		{ "key-data-lexcial-error-chars_en.impex" }, "INSERT_UPDATE Media;$contentCV[lang=en];");



	}


	private void assertFileContains(final String rootgenerated, final String[] fileNames, final String containsString)
			throws IOException
	{
		final File generatedDir = new File(rootDir.getFile(), rootgenerated);
		for (final String expectedFileName : fileNames)
		{
			final File file = new File(generatedDir, expectedFileName);
			boolean contains = false;
			final List<String> lines = FileUtils.readLines(file);
			for (final String line : lines)
			{
				if (line.equals(containsString))
				{
					contains = true;
					break;
				}
			}
			Assert.assertTrue("File " + file + " should contain line [" + containsString + "]", contains);
		}

	}


}
