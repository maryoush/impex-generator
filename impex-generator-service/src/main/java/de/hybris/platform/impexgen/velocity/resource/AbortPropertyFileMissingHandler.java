/**
 * 
 */
package de.hybris.platform.impexgen.velocity.resource;

import de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;


/**
 * @author mariusz.donigiewicz
 * 
 */
public class AbortPropertyFileMissingHandler implements PropertyFileMissingHandler
{

	@Override
	public void handle(final ScriptUrlContext ctx, final Resource resource)
	{
		throw new ResourceNotFoundException("Missing property file " + resource.getFilename());
	}
}
