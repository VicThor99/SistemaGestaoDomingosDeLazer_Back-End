package br.com.paraisopolis.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {

    private String username;
    private String senha;

}
