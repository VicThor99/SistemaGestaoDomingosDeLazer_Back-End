package br.com.domingosdelazer.SistemaGestao.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(){
        super("Senha inválida!");
    }

}
