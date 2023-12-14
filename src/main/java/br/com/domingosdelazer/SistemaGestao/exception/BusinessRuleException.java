package br.com.domingosdelazer.SistemaGestao.exception;

public class BusinessRuleException extends RuntimeException{

    public BusinessRuleException(String message){
        super(message);
    }

}
