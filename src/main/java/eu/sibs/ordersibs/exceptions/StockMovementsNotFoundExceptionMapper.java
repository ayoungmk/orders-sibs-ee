package eu.sibs.ordersibs.exceptions;

import eu.sibs.ordersibs.model.ApiError;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StockMovementsNotFoundExceptionMapper implements ExceptionMapper<StockMovementsNotFoundException> {
	
	 private final static Logger LOGGER = Logger.getLogger(StockMovementsNotFoundExceptionMapper.class);

	@Override
	public Response toResponse(StockMovementsNotFoundException exception) {
		String errorMessage = exception.getMessage();
		int errorCode = Status.NOT_FOUND.getStatusCode();
		String documentation = "These Stock Movements could not be found.";
		ApiError error = new ApiError(errorMessage, errorCode, documentation);
		
		LOGGER.warn("Something went wrong!", exception);
		LOGGER.info("A response with the error is being created by the StockMovementsNotFoundExceptionMapper");
		
		return Response.status(errorCode)
					.entity(error)
					.build();
	}

}
