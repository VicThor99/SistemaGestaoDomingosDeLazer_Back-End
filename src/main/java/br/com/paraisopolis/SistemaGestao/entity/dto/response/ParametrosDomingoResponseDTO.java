package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParametrosDomingoResponseDTO {

    private Integer total;
    private Integer aptas;
    private Integer risco;

}
