package br.com.paraisopolis.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ImportRequestDTO {

    private List<AlunoRequestDTO> alunos;

}
