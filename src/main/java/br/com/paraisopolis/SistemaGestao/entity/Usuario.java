package br.com.paraisopolis.SistemaGestao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "admin")
    private boolean admin;

    @Column(name = "username")
    @NotEmpty(message = "{user.username.mandatory}")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    @NotEmpty(message = "{user.password.mandatory}")
    private String senha;

}
