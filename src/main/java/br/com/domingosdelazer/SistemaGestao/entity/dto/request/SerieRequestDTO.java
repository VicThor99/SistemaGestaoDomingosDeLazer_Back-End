package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import br.com.domingosdelazer.SistemaGestao.entity.Sala;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class SerieRequestDTO {

    private Integer id;
    private String serie;
    private String domingo;
    private String sala;

}