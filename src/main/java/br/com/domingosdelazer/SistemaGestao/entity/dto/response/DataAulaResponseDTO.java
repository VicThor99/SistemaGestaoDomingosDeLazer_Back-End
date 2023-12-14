package br.com.domingosdelazer.SistemaGestao.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataAulaResponseDTO {

    private Integer id;
    private String date;
    private String domingo;

}
