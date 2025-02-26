package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroAlunoEspecificoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public void darFalta(LocalDate date, String domingo, Integer escolaId) {
        switch (date.getMonth()) {
            case FEBRUARY:
                this.repository.darFaltaParaAlunosFevereiro(domingo, escolaId);
                break;
            case MARCH:
                this.repository.darFaltaParaAlunosMarco(domingo, escolaId);
                break;
            case APRIL:
                this.repository.darFaltaParaAlunosAbril(domingo, escolaId);
                break;
            case MAY:
                this.repository.darFaltaParaAlunosMaio(domingo, escolaId);
                break;
            case JUNE:
                this.repository.darFaltaParaAlunosJunho(domingo, escolaId);
                break;
            case AUGUST:
                this.repository.darFaltaParaAlunosAgosto(domingo, escolaId);
                break;
            case SEPTEMBER:
                this.repository.darFaltaParaAlunosSetembro(domingo, escolaId);
                break;
            case OCTOBER:
                this.repository.darFaltaParaAlunosOutubro(domingo, escolaId);
                break;
            case NOVEMBER:
                this.repository.darFaltaParaAlunosNovembro(domingo, escolaId);
                break;
        }
    }

    public void darPresencaParaLista(List<String> codigos, DataAula dataAula, Integer escolaId) {
        switch(dataAula.getDataAula().getMonth()){
            case FEBRUARY:
                this.repository.darPresencaParaListaFevereiro(codigos, escolaId);
                break;
            case MARCH:
                this.repository.darPresencaParaListaMarco(codigos, escolaId);
                break;
            case APRIL:
                this.repository.darPresencaParaListaAbril(codigos, escolaId);
                break;
            case MAY:
                this.repository.darPresencaParaListaMaio(codigos, escolaId);
                break;
            case JUNE:
                this.repository.darPresencaParaListaJunho(codigos, escolaId);
                break;
            case AUGUST:
                this.repository.darPresencaParaListaAgosto(codigos, escolaId);
                break;
            case SEPTEMBER:
                this.repository.darPresencaParaListaSetembro(codigos, escolaId);
                break;
            case OCTOBER:
                this.repository.darPresencaParaListaOutubro(codigos, escolaId);
                break;
            case NOVEMBER:
                this.repository.darPresencaParaListaNovembro(codigos, escolaId);
                break;
            default:
                throw new RuntimeException("Não aceitamos o mês selecionado");
        }
    }
}
