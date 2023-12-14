package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SalvarDataRequestDTO {

    private Integer id;
    private Date date;
    private String domingo;

}
