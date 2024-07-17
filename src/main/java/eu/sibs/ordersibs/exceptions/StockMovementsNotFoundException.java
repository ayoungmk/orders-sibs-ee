package eu.sibs.ordersibs.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class StockMovementsNotFoundException extends RuntimeException {

	public StockMovementsNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
