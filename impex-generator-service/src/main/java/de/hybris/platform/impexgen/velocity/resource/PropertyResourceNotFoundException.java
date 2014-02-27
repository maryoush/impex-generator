/**
 * 
 */
package de.hybris.platform.impexgen.velocity.resource;

import de.hybris.platform.impexgen.velocity.VelocityExecutor;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.core.io.Resource;


/**
 * Specific exception, if thrown handled in the
 * {@link VelocityExecutor#execute(de.hybris.platform.impexgen.velocity.resource.url.ScriptUrlContext)} and
 * threaten especially so no impex is not copied over to target folder
 */
public class PropertyResourceNotFoundException extends ResourceNotFoundException
{
	private final Resource resource;

	public PropertyResourceNotFoundException(final Resource resource, final String message)
	{
		super(message);
		this.resource = resource;
	}

	public Resource getResource()
	{
		return resource;
	}

}
