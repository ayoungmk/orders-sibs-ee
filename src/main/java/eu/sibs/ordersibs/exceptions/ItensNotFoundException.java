package eu.sibs.ordersibs.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class ItensNotFoundException extends RuntimeException {

	public ItensNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
