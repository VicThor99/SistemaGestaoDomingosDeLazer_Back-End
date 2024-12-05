package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SalvarDataRequestDTO {

    private Integer id;
    private LocalDate date;
    private String domingo;

}
