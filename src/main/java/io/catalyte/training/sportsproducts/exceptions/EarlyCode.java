package io.catalyte.training.sportsproducts.exceptions;

public class EarlyCode extends RuntimeException{
  EarlyCode(){}
  public EarlyCode(String message){
    super(message);
  }
}
