package com.hoteldetails.pad.exception;

public class HotelException extends RuntimeException {

    private final String code;

    public HotelException(String message, String code) {
        this(message, null, code);
    }

    public HotelException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
