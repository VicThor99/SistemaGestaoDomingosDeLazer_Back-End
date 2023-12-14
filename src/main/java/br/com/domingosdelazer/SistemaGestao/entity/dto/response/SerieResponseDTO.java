package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SerieResponseDTO {

    private Integer id;
    private String serie;
    private String sala;
    private String domingo;

}
