package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAcessoResponseDTO {

    private Integer idEscola;
    private String escola;

}
