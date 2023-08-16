package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String username;
    private String token;

}
