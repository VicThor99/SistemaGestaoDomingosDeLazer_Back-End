package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAcessoRequestDTO {

    private Integer id;
    private Integer escolaId;
    private Integer usuarioId;

}
