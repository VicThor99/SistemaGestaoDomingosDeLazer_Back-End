package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private String cargo;

}
