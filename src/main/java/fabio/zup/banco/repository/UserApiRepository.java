package fabio.zup.banco.repository;

import fabio.zup.banco.model.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserApiRepository extends JpaRepository<UserApi, Long> {

    Optional<UserApi> findByEmail(String email);
}
