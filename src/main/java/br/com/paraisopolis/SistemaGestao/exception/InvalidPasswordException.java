package br.com.paraisopolis.SistemaGestao.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(){
        super("Senha inválida!");
    }

}
