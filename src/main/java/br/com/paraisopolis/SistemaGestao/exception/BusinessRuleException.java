package br.com.paraisopolis.SistemaGestao.exception;

public class BusinessRuleException extends RuntimeException{

    public BusinessRuleException(String message){
        super(message);
    }

}
