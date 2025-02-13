package br.com.domingosdelazer.SistemaGestao.config;

import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.RegistroPresencaServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ScheduledTasks {

    @Autowired
    private RegistroPresencaServiceImpl registroService;

    @Autowired
    private DataAulaServiceImpl dataAulaService;

    @Scheduled(cron = "0 30 4 ? * SUN")
    public void gerarFaltasParaDiaDeLazer() {
        List<DataAula> aulasHoje = this.dataAulaService.getAulaNesteDia();

        if(aulasHoje.isEmpty())
            System.out.println("Não temos aulas em nenhuma escola neste domingo!");
        else {
            for(DataAula aula : aulasHoje){
                darFalta(aula.getDataAula(), aula.getDomingo(), aula.getEscola().getId());
            }
        }
    }

    private void darFalta(LocalDate date, String domingo, Integer escolaId) {
        this.registroService.darFalta(date, domingo, escolaId);
    }

}
