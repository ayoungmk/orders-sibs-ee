package eu.sibs.ordersibs.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class StockNotFoundException extends RuntimeException {

	public StockNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
