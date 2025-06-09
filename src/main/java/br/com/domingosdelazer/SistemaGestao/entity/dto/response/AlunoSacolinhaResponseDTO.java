package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoSacolinhaResponseDTO {

    private String codigo;
    private String nome;
    private Integer idade;
    private String nascimento;
    private String sexo;
    private String turma;
    private String grupo;
    private Integer sapato;
    private Integer blusa;
    private Integer calca;
    private String responsavel;
    private String telefone;

}
