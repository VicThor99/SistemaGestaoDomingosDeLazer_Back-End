package br.com.domingosdelazer.SistemaGestao.service.impl;


import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
import br.com.domingosdelazer.SistemaGestao.entity.UsuarioAcesso;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.UsuarioAcessoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.EscolaRepository;
import br.com.domingosdelazer.SistemaGestao.repository.UsuarioAcessoRepository;
import br.com.domingosdelazer.SistemaGestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioAcessoServiceImpl {

    @Autowired
    private EscolaRepository escolaRepository;

    @Autowired
    private UsuarioAcessoRepository repository;

    @Transactional
    public UsuarioAcesso save(UsuarioAcesso access) {
        return this.repository.save(access);
    }

    public List<UsuarioAcessoResponseDTO> listAll() {
        List<UsuarioAcessoResponseDTO> acessos = new ArrayList<>();
        this.repository.findAll().forEach(acesso -> {
            acessos.add(UsuarioAcessoResponseDTO
                    .builder()
                    .idEscola(acesso.getEscola().getId())
                    .escola(acesso.getEscola().getNome())
                    .build());
        });

        return acessos;
    }

    public List<UsuarioAcessoResponseDTO> listAccessForUser(Usuario user) {
        List<UsuarioAcessoResponseDTO> acessos = new ArrayList<>();
        if (user.isAdmin()) {
            this.escolaRepository.findAll().forEach(escola -> {
                acessos.add(UsuarioAcessoResponseDTO
                        .builder()
                        .idEscola(escola.getId())
                        .escola(escola.getNome())
                        .build());
            });
        } else {
            this.repository.listAccessForUser(user.getId()).forEach(acesso -> {
                acessos.add(UsuarioAcessoResponseDTO
                        .builder()
                        .idEscola(acesso.getEscola().getId())
                        .escola(acesso.getEscola().getNome())
                        .build());
            });
        }

        return acessos;
    }
}
