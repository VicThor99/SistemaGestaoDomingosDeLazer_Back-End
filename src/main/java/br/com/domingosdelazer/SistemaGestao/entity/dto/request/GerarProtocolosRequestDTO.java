package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GerarProtocolosRequestDTO {

    private String domingo;
    private String codigo;

}