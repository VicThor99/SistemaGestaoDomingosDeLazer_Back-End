package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SalvarAlunoRequestDTO {

    private Integer id;
    private String codigo;
    private String numeroSacolinha;
    private String nome;
    private String sexo;
    private String serie;
    private LocalDate nascimento;
    private Integer sapato;
    private Integer calca;
    private Integer blusa;
    private String endereco;
    private String nomeResponsavel;
    private String telefoneResponsavel;
    private String emailResponsavel;
    private String observacoes;
    private Boolean ativo;
    private Boolean sairSo;
    private Integer escolaId;
}
