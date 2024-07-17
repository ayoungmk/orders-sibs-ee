package eu.sibs.ordersibs.exceptions;

import eu.sibs.ordersibs.model.ApiError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StockNotFoundExceptionMapper implements ExceptionMapper<StockNotFoundException> {
	
	 private final static Logger LOGGER = Logger.getLogger(StockNotFoundExceptionMapper.class);

	@Override
	public Response toResponse(StockNotFoundException exception) {
		String errorMessage = exception.getMessage();
		int errorCode = Status.NOT_FOUND.getStatusCode();
		String documentation = "The Stock could not be found.";
		ApiError error = new ApiError(errorMessage, errorCode, documentation);
		
		LOGGER.warn("Something went wrong!", exception);
		LOGGER.info("A response with the error is being created by the StockNotFoundExceptionMapper");
		
		return Response.status(errorCode)
					.entity(error)
					.build();
	}

}
