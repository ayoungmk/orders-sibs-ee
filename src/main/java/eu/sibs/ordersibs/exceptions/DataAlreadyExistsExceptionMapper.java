package eu.sibs.ordersibs.exceptions;

import eu.sibs.ordersibs.model.ApiError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataAlreadyExistsExceptionMapper implements ExceptionMapper<DataAlreadyExistsException> {
	
	 private final static Logger LOGGER = Logger.getLogger(DataAlreadyExistsExceptionMapper.class);
	
	@Override
	public Response toResponse(DataAlreadyExistsException exception) {
		String errorMessage = exception.getMessage();
		int errorCode = Status.CONFLICT.getStatusCode();
		String documentation = "This data already exists.";
		ApiError error = new ApiError(errorMessage, errorCode, documentation);
		
		LOGGER.warn("Something went wrong!", exception);
		LOGGER.info("A response with the error is being created by the DataAlreadyExistsExceptionMapper");
		
		return Response.status(errorCode)
					.entity(error)
					.build();
	}
}
