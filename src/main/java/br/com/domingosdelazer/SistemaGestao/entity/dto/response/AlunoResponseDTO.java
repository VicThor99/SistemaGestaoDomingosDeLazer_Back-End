package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlunoResponseDTO {

    private Integer id;
    private String codigo;
    private String nome;
    private String sexo;
    private String serie;
    private String sala;
    private String nascimento;
    private Integer sapato;
    private Integer blusa;
    private Integer calca;
    private String endereco;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private String emailResponsavel;
    private String numeroSacolinha;
    private Boolean ativo;
    private Boolean sairSo;
    private String observacoes;

}
