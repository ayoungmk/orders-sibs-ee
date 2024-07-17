package eu.sibs.ordersibs.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import eu.sibs.ordersibs.model.ApiError;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	 private final static Logger LOGGER = Logger.getLogger(GenericExceptionMapper.class);

	@Override
	public Response toResponse(Throwable exception) {
		String errorMessage = exception.getMessage();
		int errorCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		String documentation = "There was an Exception.";
		ApiError error = new ApiError(errorMessage, errorCode, documentation);

		LOGGER.warn("Something went wrong!", exception);
		LOGGER.info("A response with the error is being created by the GenericExceptionMapper");

		return Response.status(errorCode)
					.entity(error)
					.build();
	}

}
