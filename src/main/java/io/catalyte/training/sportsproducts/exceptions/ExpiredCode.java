package io.catalyte.training.sportsproducts.exceptions;

public class ExpiredCode extends RuntimeException{

    public ExpiredCode() {
    }

    public ExpiredCode(String message) {
      super(message);
    }
}
