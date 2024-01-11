package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroAlunoEspecificoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class RegistroPresencaServiceImpl {

    @Autowired
    private RegistroPresencasRepository repository;

    public List<RegistroPresencaAlunoResponseDTO> getListaRegistros(Integer escolaId) {
        return repository.carregarRegistrosAlunos(escolaId);
    }

    public RegistroAlunoEspecificoResponseDTO getRegistrosPorCodigoAluno(String codigo, Integer escolaId) {
        RegistroPresencas reg = this.repository.carregarRegistroPorCodigoAluno(codigo, escolaId);
        return RegistroAlunoEspecificoResponseDTO
                .builder()
                .fevereiro(reg.getFevereiro().getSigla())
                .marco(reg.getMarco().getSigla())
                .abril(reg.getAbril().getSigla())
                .maio(reg.getMaio().getSigla())
                .junho(reg.getJunho().getSigla())
                .agosto(reg.getAgosto().getSigla())
                .setembro(reg.getSetembro().getSigla())
                .outubro(reg.getOutubro().getSigla())
                .novembro(reg.getNovembro().getSigla())
                .build();
    }

    public RegistroPresencas getRegistroByCodigoAluno(String codigo, Integer escolaId) {
        return this.repository.carregarRegistroPorCodigoAluno(codigo, escolaId);
    }

    public RegistroPresencas save(RegistroPresencas registroPresencas) {
        return this.repository.save(registroPresencas);
    }

    @Transactional
    public void darFalta(Calendar cal, String domingo, Integer escolaId) {
        switch (cal.get(Calendar.MONTH)) {
            case Calendar.FEBRUARY:
                this.repository.darFaltaParaAlunosFevereiro(domingo, escolaId);
                break;
            case Calendar.MARCH:
                this.repository.darFaltaParaAlunosMarco(domingo, escolaId);
                break;
            case Calendar.APRIL:
                this.repository.darFaltaParaAlunosAbril(domingo, escolaId);
                break;
            case Calendar.MAY:
                this.repository.darFaltaParaAlunosMaio(domingo, escolaId);
                break;
            case Calendar.JUNE:
                this.repository.darFaltaParaAlunosJunho(domingo, escolaId);
                break;
            case Calendar.AUGUST:
                this.repository.darFaltaParaAlunosAgosto(domingo, escolaId);
                break;
            case Calendar.SEPTEMBER:
                this.repository.darFaltaParaAlunosSetembro(domingo, escolaId);
                break;
            case Calendar.OCTOBER:
                this.repository.darFaltaParaAlunosOutubro(domingo, escolaId);
                break;
            case Calendar.NOVEMBER:
                this.repository.darFaltaParaAlunosNovembro(domingo, escolaId);
                break;
        }
    }
}
