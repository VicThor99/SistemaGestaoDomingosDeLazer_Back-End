package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoSacolinhaResponseDTO {

    private String codigo;
    private String nome;
    private String nascimento;
    private Integer sapato;
    private Integer calca;
    private Integer camisa;
    private String serie;
    private String sala;

}
