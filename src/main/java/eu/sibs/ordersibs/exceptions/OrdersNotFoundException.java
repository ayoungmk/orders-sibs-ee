package eu.sibs.ordersibs.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class OrdersNotFoundException extends RuntimeException {

	public OrdersNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
