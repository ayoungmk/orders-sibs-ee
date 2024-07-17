package eu.sibs.ordersibs.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import eu.sibs.ordersibs.model.ApiError;

public class ItensNotFoundExceptionMapper implements ExceptionMapper<ItensNotFoundException>{
	
	private final static Logger LOGGER = Logger.getLogger(ItensNotFoundExceptionMapper.class);

	@Override
	public Response toResponse(ItensNotFoundException exception) {
		String errorMessage = exception.getMessage();
		int errorCode = Status.NOT_FOUND.getStatusCode();
		String documentation = "These itens could not be found.";
		ApiError error = new ApiError(errorMessage, errorCode, documentation);
		
		LOGGER.warn("Something went wrong!", exception);
		LOGGER.info("A response with the error is being created by the ItensNotFoundExceptionMapper");
		
		return Response.status(errorCode)
					.entity(error)
					.build();
	}

}
