package se.yrgo.dataaccess;

import java.io.Serial;

public class RecordNotFoundException extends Exception {

	/**
	 * Just to stop warnings.
	 */
	@Serial
	private static final long serialVersionUID = 1L;
    private final String s;

    public RecordNotFoundException(String s) {
        this.s = s;
    }
}
