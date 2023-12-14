package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadastroUsuarioResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private String cargo;

}
