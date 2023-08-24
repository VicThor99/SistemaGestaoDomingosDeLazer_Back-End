package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoResponseDTO {

    private String nome;
    private String codigo;
    private String serie;
    private String sala;
    private String nascimento;

}
