package br.com.paraisopolis.SistemaGestao.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName){
        super(entityName + " não encontrado!");
    }

}
