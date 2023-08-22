package br.com.paraisopolis.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "numeracao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Numeracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sapato")
    private Integer sapato;

    @Column(name = "camisa")
    private Integer camisa;

    @Column(name = "calca")
    private Integer calca;

}
