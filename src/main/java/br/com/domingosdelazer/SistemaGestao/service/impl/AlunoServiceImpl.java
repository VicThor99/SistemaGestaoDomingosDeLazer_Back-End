package br.com.domingosdelazer.SistemaGestao.service.impl;

import br.com.domingosdelazer.SistemaGestao.exception.BusinessRuleException;
import br.com.domingosdelazer.SistemaGestao.repository.SerieRepository;
import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DadosGraficoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlunoServiceImpl {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private SerieRepository serieRepository;

    public List<Aluno> listAllAlunos(boolean sacolinhas) {
        if (sacolinhas) {
            return repository.listAlunosAptosASacolinha();
        } else {
            return repository.findAll();
        }
    }

    public Aluno save(Aluno aluno) {
        verificarAluno(aluno);
        return this.repository.save(aluno);
    }

    public Integer countAlunosAptosASacolinha(String domingo) {
        return this.repository.countAlunosAptosASacolinha(domingo);
    }

    public Integer countAlunosEmRisco(String domingo) {
        return this.repository.countAlunosEmRisco(domingo);
    }

    public Integer countAlunosTotal(String domingo) {
        return this.repository.countAlunosTotal(domingo);
    }

    public List<DadosGraficoResponseDTO> getPresencas(String domingo, DataAula dataAula) {
        List<DadosGraficoResponseDTO> lista = new ArrayList<>();

        if (dataAula != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataAula.getDataAula());

            if (cal.get(Calendar.MONTH) > Calendar.FEBRUARY)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesFevereiro(domingo)).label("Fevereiro").build());

            if (cal.get(Calendar.MONTH) > Calendar.MARCH)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMarco(domingo)).label("Março").build());

            if (cal.get(Calendar.MONTH) > Calendar.APRIL)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAbril(domingo)).label("Abril").build());

            if (cal.get(Calendar.MONTH) > Calendar.MAY)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMaio(domingo)).label("Maio").build());

            if (cal.get(Calendar.MONTH) > Calendar.JUNE)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesJunho(domingo)).label("Junho").build());

            if (cal.get(Calendar.MONTH) > Calendar.AUGUST)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAgosto(domingo)).label("Agosto").build());

            if (cal.get(Calendar.MONTH) > Calendar.SEPTEMBER)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesSetembro(domingo)).label("Setembro").build());

            if (cal.get(Calendar.MONTH) > Calendar.OCTOBER)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesOutubro(domingo)).label("Outubro").build());

            if (cal.get(Calendar.MONTH) > Calendar.NOVEMBER)
                lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesNovembro(domingo)).label("Novembro").build());
        } else {
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesFevereiro(domingo)).label("Fevereiro").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMarco(domingo)).label("Março").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAbril(domingo)).label("Abril").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMaio(domingo)).label("Maio").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesJunho(domingo)).label("Junho").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAgosto(domingo)).label("Agosto").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesSetembro(domingo)).label("Setembro").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesOutubro(domingo)).label("Outubro").build());
            lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesNovembro(domingo)).label("Novembro").build());
        }
        return lista;
    }

    public String carregarCodigoPorSerie(String serie) {
        String domingo = this.serieRepository.getDomingoPorSerie(serie);
        String ultimoCodigo = this.repository.getUltimoCodigoPorDomingo(domingo);
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

    public List<Aluno> getDashboardLista(String filtro) {
        switch (filtro.toLowerCase()) {
            case "domaaptas":
                return this.repository.getAlunosAptosASacolinha("A");
            case "domarisco":
                return this.repository.getAlunosEmRiscoASacolinha("A");
            case "dombaptas":
                return this.repository.getAlunosAptosASacolinha("B");
            case "dombrisco":
                return this.repository.getAlunosEmRiscoASacolinha("B");
            case "todosaptos":
                return this.repository.getAlunosAptosASacolinha();
            case "todosrisco":
                return this.repository.getAlunosEmRiscoASacolinha();
        }
        return null;
    }
}
