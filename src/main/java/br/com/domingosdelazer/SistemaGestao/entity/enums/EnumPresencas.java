package br.com.domingosdelazer.SistemaGestao.entity.enums;

import lombok.Getter;

public enum EnumPresencas {

    N("","Não preenchido"),P("P","Presença"), M("M","Presença Manual"), A("A","Atestado"), E("E","Esqueceu o crachá"), F("F","Falta");

    @Getter
    private String sigla;
    private String descricao;

    EnumPresencas(String sigla, String descricao){
        this.sigla = sigla;
        this.descricao = descricao;
    }

}
