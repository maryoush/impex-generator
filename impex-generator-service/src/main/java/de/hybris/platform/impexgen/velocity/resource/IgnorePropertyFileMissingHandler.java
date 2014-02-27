/**
 * 
 */
package de.hybris.platform.impexgen.velocity.resource;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class IgnorePropertyFileMissingHandler implements PropertyFileMissingHandler
{
	public static final Logger LOG = Logger.getLogger(IgnorePropertyFileMissingHandler.class);


	@Override
	public void handle(final ScriptUrlContext ctx, final Resource resource)
	{
		if (LOG.isDebugEnabled())
		{
			try
			{
				LOG.debug("Missing property file [" + resource.getFile().getAbsolutePath() + "]");
			}
			catch (final IOException e)
			{
				LOG.debug("Can not access property file [" + resource.getFilename() + "]");
			}
		}
	}
}
