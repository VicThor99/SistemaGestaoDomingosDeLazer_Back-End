package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import br.com.domingosdelazer.SistemaGestao.entity.enums.EnumPresencas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AlunosComPresencasResponseDTO {

    private String codigo;
    private String nome;
    private String presenca;

    public AlunosComPresencasResponseDTO(String codigo, String nome, EnumPresencas presencaMesAtual) {
        this.codigo = codigo;
        this.nome = nome;
        this.presenca = presencaMesAtual.getSigla() + " - " + presencaMesAtual.getDescricao();
    }
}
