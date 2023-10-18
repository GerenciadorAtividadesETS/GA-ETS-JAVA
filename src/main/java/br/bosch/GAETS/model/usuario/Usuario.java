package br.bosch.GAETS.model.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Table(name = "Usuarios")
@Entity(name = "Usuario")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int turma;
    private String edv, nome, senha;
    private boolean ativo, instrutor;

    public Usuario(DadosCadastroUsuario dadosCadastroUsuario) {
        this.turma = dadosCadastroUsuario.turma();
        this.edv = dadosCadastroUsuario.edv();
        this.nome = dadosCadastroUsuario.nome();
        this.senha = dadosCadastroUsuario.senha();
        this.ativo = true;
        this.instrutor = dadosCadastroUsuario.instrutor();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return edv;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}