package se.yrgo.services.customers;

import se.yrgo.dataaccess.RecordNotFoundException;

import java.io.Serial;

public class CustomerNotFoundException extends Exception {
	// this is just to stop the annoying warning in Eclipse.
	@Serial
    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(String s, RecordNotFoundException e) {
    }
}
