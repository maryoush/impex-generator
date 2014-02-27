/**
 * 
 */
package de.hybris.platform.impexgen.velocity.resource;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class RemoveTargetFileMissingHandler implements PropertyFileMissingHandler
{
	public static final Logger LOG = Logger.getLogger(RemoveTargetFileMissingHandler.class);


	@Override
	public void handle(final ScriptUrlContext ctx, final Resource resource)
	{
		throw new PropertyResourceNotFoundException(resource, "Missing property file [" + resource.getFilename()
				+ "] , avoiding to generate target impex script [" + ctx.getTargetScriptEffectivePath() + "]");
	}
}
