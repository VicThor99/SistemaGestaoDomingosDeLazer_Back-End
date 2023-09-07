package br.com.paraisopolis.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dataaula")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dataaula")
    private Date dataAula;

    @Column(name = "domingo")
    private String domingo;

}
