package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CorrecaoRegistroRequestDTO {

    private String codigo;
    private String fevereiro;
    private String marco;
    private String abril;
    private String maio;
    private String junho;
    private String agosto;
    private String setembro;
    private String outubro;
    private String novembro;

}
