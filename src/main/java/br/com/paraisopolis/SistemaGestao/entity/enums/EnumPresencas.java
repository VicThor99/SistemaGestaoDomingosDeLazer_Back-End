package br.com.paraisopolis.SistemaGestao.entity.enums;

public enum EnumPresencas {

    P("Presença"), M("Presença Manual"), A("Atestado"), E("Esqueceu o crachá"), F("Falta");

    private String descricao;

    EnumPresencas(String descricao){
        this.descricao = descricao;
    }

}
