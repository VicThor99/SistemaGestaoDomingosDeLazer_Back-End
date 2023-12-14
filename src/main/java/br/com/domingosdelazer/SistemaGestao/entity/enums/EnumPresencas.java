package br.com.domingosdelazer.SistemaGestao.entity.enums;

public enum EnumPresencas {

    P("P","Presença"), M("M","Presença Manual"), A("A","Atestado"), E("E","Esqueceu o crachá"), F("F","Falta");

    private String sigla;
    private String descricao;

    EnumPresencas(String sigla, String descricao){
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }
}
