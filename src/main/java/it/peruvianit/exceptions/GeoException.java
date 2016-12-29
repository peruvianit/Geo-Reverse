/**
 * Clase generica per gli errori dell'applicazione
 * 
 * @author Sergio Arellano Diaz
 * @version 1.0.1
 * @since 17/12/2016
 *
 */
package it.peruvianit.exceptions;

public class GeoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4064605798689582965L;

	public GeoException() {
	}

	public GeoException(final String message) {
		super(message);
	}

	public GeoException(final Throwable cause) {
		super(cause);
	}

	public GeoException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GeoException(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}