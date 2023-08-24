package br.com.paraisopolis.SistemaGestao.service.impl;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
