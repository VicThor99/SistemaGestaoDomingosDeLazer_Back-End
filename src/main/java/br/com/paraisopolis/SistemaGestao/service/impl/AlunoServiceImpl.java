package br.com.paraisopolis.SistemaGestao.service.impl;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.DadosGraficoResponseDTO;
import br.com.paraisopolis.SistemaGestao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoServiceImpl {

    @Autowired
    private AlunoRepository repository;

    public List<Aluno> listAllAlunos(boolean sacolinhas){
        if(sacolinhas){
            return repository.listAlunosAptosASacolinha();
        } else {
            return repository.findAll();
        }
    }

    public Aluno save(Aluno aluno){
        return this.repository.save(aluno);
    }

    public Integer countAlunosAptosASacolinha(String domingo){
        return this.repository.countAlunosAptosASacolinha(domingo);
    }

    public Integer countAlunosEmRisco(String domingo){
        return this.repository.countAlunosEmRisco(domingo);
    }

    public Integer countAlunosTotal(String domingo){
        return this.repository.countAlunosTotal(domingo);
    }

    public List<DadosGraficoResponseDTO> getPresencas(String domingo) {
        List<DadosGraficoResponseDTO> lista = new ArrayList<>();

        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesFevereiro(domingo)).label("Fevereiro").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMarco(domingo)).label("Marco").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAbril(domingo)).label("Abril").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesMaio(domingo)).label("Maio").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesJunho(domingo)).label("Junho").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesAgosto(domingo)).label("Agosto").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesSetembro(domingo)).label("Setembro").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesOutubro(domingo)).label("Outubro").build());
        lista.add(DadosGraficoResponseDTO.builder().y(this.repository.countAlunosPresentesNovembro(domingo)).label("Novembro").build());

        return lista;
    }
}
