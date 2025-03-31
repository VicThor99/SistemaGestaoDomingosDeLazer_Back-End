package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContagemResponseDTO {

    private String sala;
    private String serie;
    private String quantidadeAlunos;

}
