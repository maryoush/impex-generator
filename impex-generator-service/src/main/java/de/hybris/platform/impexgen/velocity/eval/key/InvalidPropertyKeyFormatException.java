/**
 * 
 */
package de.hybris.platform.impexgen.velocity.eval.key;

/**
 * @author mariusz.donigiewicz
 * 
 */
public class InvalidPropertyKeyFormatException extends RuntimeException
{

	public InvalidPropertyKeyFormatException(final String str)
	{
		super(str);
	}

	public InvalidPropertyKeyFormatException(final Throwable ex)
	{
		super(ex);
	}
}
