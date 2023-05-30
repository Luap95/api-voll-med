package med.voll.api.domain.usuario;

import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends Repository<Usuario, Long> {
    UserDetails findByLogin(String login);
}