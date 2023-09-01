package br.com.paraisopolis.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AlunoRequestDTO {

    private String nome;
    private Date nascimento;
    private String serie;

}
