package br.com.domingosdelazer.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "arquivosaluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArquivosAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "foto")
    private Blob foto;

    @Column(name = "matricula")
    private Blob matricula;

}
