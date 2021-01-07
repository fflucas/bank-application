package fabio.zup.banco.service;

import fabio.zup.banco.model.UserApi;

import java.util.Optional;


public interface UserApiService {

    Optional<UserApi> findById(Long userId);

    Optional<UserApi> findByEmail(String email);
}
