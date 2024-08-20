package com.objetivo.auth.repository;

import com.objetivo.auth.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByResetToken(String resetToken);

}
