package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class RegistroPresencaServiceImpl {

    @Autowired
    private RegistroPresencasRepository repository;

    public List<RegistroPresencaAlunoResponseDTO> getListaRegistros() {
        return repository.carregarRegistrosAlunos();
    }

    public RegistroPresencas getRegistroByCodigoAluno(String codigo) {
        return this.repository.carregarRegistroPorCodigoAluno(codigo);
    }

    public RegistroPresencas save(RegistroPresencas registroPresencas) {
        return this.repository.save(registroPresencas);
    }

    @Transactional
    public void darFalta(Calendar cal, String domingo) {
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.FEBRUARY:
                this.repository.darFaltaParaAlunosFevereiro(domingo);
                break;
            case Calendar.MARCH:
                this.repository.darFaltaParaAlunosMarco(domingo);
                break;
            case Calendar.APRIL:
                this.repository.darFaltaParaAlunosAbril(domingo);
                break;
            case Calendar.MAY:
                this.repository.darFaltaParaAlunosMaio(domingo);
                break;
            case Calendar.JUNE:
                this.repository.darFaltaParaAlunosJunho(domingo);
                break;
            case Calendar.AUGUST:
                this.repository.darFaltaParaAlunosAgosto(domingo);
                break;
            case Calendar.SEPTEMBER:
                this.repository.darFaltaParaAlunosSetembro(domingo);
                break;
            case Calendar.OCTOBER:
                this.repository.darFaltaParaAlunosOutubro(domingo);
                break;
            case Calendar.NOVEMBER:
                this.repository.darFaltaParaAlunosNovembro(domingo);
                break;
        }
    }
}
