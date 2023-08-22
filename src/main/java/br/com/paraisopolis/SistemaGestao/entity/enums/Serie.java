package br.com.paraisopolis.SistemaGestao.entity.enums;

public enum Serie {

    PRIMEIRO_A("1ºA", "Sala 6"), PRIMEIRO_B("1ºB", "Sala 8"), PRIMEIRO_C("1ºC", "Sala 9"),
    PRIMEIRO_D("1ºD", "Sala 10"), PRIMEIRO_E("1ºE", "Sala 11"), PRIMEIRO_F("1ºF", "Sala 12"),
    SEGUNDO_A("2ºA", "Sala 13"), SEGUNDO_B("2ºB", "Sala 14"), SEGUNDO_C("2ºC", "Sala 15"),
    SEGUNDO_D("2ºD", "Sala 16"), SEGUNDO_E("2ºE", "Sala 17"), SEGUNDO_F("2ºF", "Sala 18"),
    TERCEIRO_A("3ºA", "Sala 2"), TERCEIRO_B("3ºB", "Sala 3"), TERCEIRO_C("3ºC", "Sala 4"),
    TERCEIRO_D("3ºD", "Sala 2"), TERCEIRO_E("3ºE", "Sala 3"), TERCEIRO_F("3ºF", "Sala 4"),
    QUARTO_A("4ºA", "Sala 6"), QUARTO_B("4ºB", "Sala 8"), QUARTO_C("4ºC", "Sala 9"),
    QUARTO_D("4ºD", "Sala 10"), QUARTO_E("4ºE", "Sala 11"), QUARTO_F("4ºF", "Sala 12"),
    QUINTO_A("5ºA", "Sala 13"), QUINTO_B("5ºB", "Sala 14"), QUINTO_C("5ºC", "Sala 15"),
    QUINTO_D("5ºD", "Sala 16"), QUINTO_E("5ºE", "Sala 17"), QUINTO_F("5ºF", "Sala 18");

    private final String descricao;

    Serie(String descricao, String sala){
        this.descricao = descricao;
    }

}
