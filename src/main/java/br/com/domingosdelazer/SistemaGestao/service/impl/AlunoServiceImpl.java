package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.exception.BusinessRuleException;
import br.com.domingosdelazer.SistemaGestao.repository.SerieRepository;
import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DadosGraficoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class AlunoServiceImpl {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private SerieRepository serieRepository;

    public List<Aluno> listAllAlunos(boolean sacolinhas, Integer escolaId) {
        if (sacolinhas) {
            return repository.listAlunosAptosASacolinha(escolaId);
        } else {
            return repository.findAllByEscolaId(escolaId);
        }
    }

    public Aluno save(Aluno aluno) {
        verificarAluno(aluno);
        return this.repository.save(aluno);
    }

    public Integer countAlunosAptosASacolinha(String domingo, Integer escolaId) {
        return this.repository.countAlunosAptosASacolinha(domingo, escolaId);
    }

    public Integer countAlunosEmRisco(String domingo, Integer escolaId) {
        return this.repository.countAlunosEmRisco(domingo, escolaId);
    }

    public Integer countAlunosTotal(String domingo, Integer escolaId) {
        return this.repository.countAlunosTotal(domingo, escolaId);
    }

    public List<DadosGraficoResponseDTO> getPresencas(String domingo, DataAula dataAula, Integer escolaId) {
        List<DadosGraficoResponseDTO> lista = null;

        if (dataAula != null) {
            if (dataAula.getDataAula().isBefore(LocalDate.now()))
                lista = new ArrayList<>();

            if (dataAula.getDataAula().getMonth().getValue() > Month.FEBRUARY.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesFevereiro(domingo, escolaId)).label("Fevereiro").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.MARCH.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMarco(domingo, escolaId)).label("Março").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.APRIL.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAbril(domingo, escolaId)).label("Abril").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.MAY.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMaio(domingo, escolaId)).label("Maio").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.JUNE.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesJunho(domingo, escolaId)).label("Junho").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.AUGUST.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAgosto(domingo, escolaId)).label("Agosto").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.SEPTEMBER.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesSetembro(domingo, escolaId)).label("Setembro").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.OCTOBER.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesOutubro(domingo, escolaId)).label("Outubro").build());

            if (dataAula.getDataAula().getMonth().getValue() > Month.NOVEMBER.getValue() && lista != null)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesNovembro(domingo, escolaId)).label("Novembro").build());
        }

        return lista;
    }

    public String carregarCodigoPorSerie(String serie, Integer escolaId) {
        String domingo = this.serieRepository.getDomingoPorSerie(serie, escolaId);
        String ultimoCodigo = this.repository.getUltimoCodigoPorDomingo(domingo, escolaId);
        if (ultimoCodigo != null) {
            return String.valueOf(Integer.parseInt(ultimoCodigo) + 1);
        } else {
            return domingo.equalsIgnoreCase("A") ? "100001" :
                    domingo.equalsIgnoreCase("B") ? "200001" :
                            domingo.equalsIgnoreCase("C") ? "300001" : "400001";
        }
    }

    private void verificarAluno(Aluno aluno) {
        if (aluno.getSerie().getDomingo().equalsIgnoreCase("A") &&
                (aluno.getCodigo().startsWith("20") || aluno.getCodigo().startsWith("30") || aluno.getCodigo().startsWith("40")))
            throw new BusinessRuleException("Aluno com código cadastrado para Domingo diferente do Domingo A, verifique a sala ou solicite a troca a um ADM");

        if (aluno.getSerie().getDomingo().equalsIgnoreCase("B") &&
                (aluno.getCodigo().startsWith("10") || aluno.getCodigo().startsWith("30") || aluno.getCodigo().startsWith("40")))
            throw new BusinessRuleException("Aluno com código cadastrado para Domingo diferente do Domingo B, verifique a sala ou solicite a troca a um ADM");

        if (aluno.getSerie().getDomingo().equalsIgnoreCase("C") &&
                (aluno.getCodigo().startsWith("10") || aluno.getCodigo().startsWith("20") || aluno.getCodigo().startsWith("40")))
            throw new BusinessRuleException("Aluno com código cadastrado para Domingo diferente do Domingo C, verifique a sala ou solicite a troca a um ADM");

        if (aluno.getSerie().getDomingo().equalsIgnoreCase("D") &&
                (aluno.getCodigo().startsWith("10") || aluno.getCodigo().startsWith("20") || aluno.getCodigo().startsWith("30")))
            throw new BusinessRuleException("Aluno com código cadastrado para Domingo diferente do Domingo D, verifique a sala ou solicite a troca a um ADM");
    }

    public List<Aluno> getDashboardLista(String filtro, Integer escolaId) {
        switch (filtro.toLowerCase()) {
            case "domaaptas":
                return this.repository.getAlunosAptosASacolinha("A", escolaId);
            case "domarisco":
                return this.repository.getAlunosEmRiscoASacolinha("A", escolaId);
            case "dombaptas":
                return this.repository.getAlunosAptosASacolinha("B", escolaId);
            case "dombrisco":
                return this.repository.getAlunosEmRiscoASacolinha("B", escolaId);
            case "domacptas":
                return this.repository.getAlunosAptosASacolinha("C", escolaId);
            case "domcrisco":
                return this.repository.getAlunosEmRiscoASacolinha("C", escolaId);
            case "domdaptas":
                return this.repository.getAlunosAptosASacolinha("D", escolaId);
            case "domdrisco":
                return this.repository.getAlunosEmRiscoASacolinha("D", escolaId);
            case "todosaptos":
                return this.repository.getAlunosAptosASacolinha(escolaId);
            case "todosrisco":
                return this.repository.getAlunosEmRiscoASacolinha(escolaId);
            default:
                return null;
        }
    }

    public Aluno getAlunoById(Integer id){
        return this.repository.findById(id).orElseGet(null);
    }

    public Aluno getAlunoByCodigo(String codigo, Integer escolaId){
        return this.repository.getAlunoPorCodigo(codigo, escolaId).orElseGet(null);
    }

}
