package br.com.domingosdelazer.SistemaGestao.entity.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class DatasEmMassaRequestDTO {

    private List<Date> datasDomA;
    private List<Date> datasDomB;
    private List<Date> datasDomC;
    private List<Date> datasDomD;

}
