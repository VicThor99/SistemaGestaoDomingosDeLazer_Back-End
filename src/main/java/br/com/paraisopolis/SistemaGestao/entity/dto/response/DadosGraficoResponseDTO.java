package br.com.paraisopolis.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DadosGraficoResponseDTO {

    private String label;
    private Integer y;

}
