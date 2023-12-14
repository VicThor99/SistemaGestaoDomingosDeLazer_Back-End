package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AlunoRequestDTO {

    private String nome;
    private String sexo;
    private Date nascimento;
    private String serie;
    private String sala;
    private String domingo;

}
