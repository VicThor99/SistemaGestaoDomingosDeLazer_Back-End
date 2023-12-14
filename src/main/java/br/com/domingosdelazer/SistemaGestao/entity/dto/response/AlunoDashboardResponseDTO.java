package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoDashboardResponseDTO {

    private Integer id;
    private String codigo;
    private String nome;
    private String sexo;
    private String serie;
    private String sala;
    private String nascimento;

}
