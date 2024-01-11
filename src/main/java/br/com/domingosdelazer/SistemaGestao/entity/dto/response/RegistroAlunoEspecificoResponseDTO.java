package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistroAlunoEspecificoResponseDTO {
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
