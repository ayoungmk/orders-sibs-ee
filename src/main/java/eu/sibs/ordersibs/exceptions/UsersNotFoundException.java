package eu.sibs.ordersibs.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException
public class UsersNotFoundException extends RuntimeException {

	public UsersNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
