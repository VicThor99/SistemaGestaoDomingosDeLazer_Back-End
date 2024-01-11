package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.repository.DataAulaRepository;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DataAulaServiceImpl {

    @Autowired
    private DataAulaRepository repository;

    public Optional<DataAula> getProximaAula(String domingo, Integer escolaId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return this.repository.getProximaAula(domingo, sdf.format(new Date()), escolaId);
    }

    public List<DataAula> listAll(Integer escolaId){
        return this.repository.listAll(escolaId);
    }

    public DataAula save(DataAula dataAula){
        return this.repository.save(dataAula);
    }


    public DataAula getAulaParaPresenca(String data, Integer escolaId) {
        return this.repository.getAulaParaPresenca(data, escolaId);
    }
}
