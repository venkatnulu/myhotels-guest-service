package com.myhotels.guestservice.exceptions;

public class GuestNotFoundException extends Exception{
    public GuestNotFoundException(String message) {
        super(message);
    }
}
