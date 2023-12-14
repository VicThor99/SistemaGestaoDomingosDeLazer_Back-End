package br.com.domingosdelazer.SistemaGestao.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName){
        super(entityName + " não encontrado!");
    }

}
