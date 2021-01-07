package fabio.zup.banco.service;

import fabio.zup.banco.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    void save(User user);

    Page<User> findAll(Pageable pagination);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    User getOne(Long id);
}
