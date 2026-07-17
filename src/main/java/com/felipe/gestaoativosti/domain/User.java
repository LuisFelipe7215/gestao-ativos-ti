package com.felipe.gestaoativosti.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Schema(description = "Entidade que representa um usuário do sistema")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do usuário", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Nome de usuário único para autenticação", example = "luis.felipe")
    private String username;

    @Column(nullable = false)
    @Schema(description = "Senha codificada do usuário", example = "$2a$10$E2UPv7arXNp3qHPf.0..zuQ1A2aWz2X4D2r0...")
    private String password;

    @Column(nullable = false)
    @Schema(description = "Roles do usuário separadas por vírgula", example = "ROLE_USER,ROLE_ADMIN")
    private String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}

