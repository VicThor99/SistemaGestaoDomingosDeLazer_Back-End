package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DatasEmMassaRequestDTO {

    private List<LocalDate> datasDomA;
    private List<LocalDate> datasDomB;
    private List<LocalDate> datasDomC;
    private List<LocalDate> datasDomD;

}
