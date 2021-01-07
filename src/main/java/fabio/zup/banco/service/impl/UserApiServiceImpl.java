package fabio.zup.banco.service.impl;

import fabio.zup.banco.model.UserApi;
import fabio.zup.banco.repository.UserApiRepository;
import fabio.zup.banco.service.UserApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApiServiceImpl implements UserApiService {

    UserApiRepository userApiRepository;
    @Autowired
    public UserApiServiceImpl(UserApiRepository userApiRepository) {
        this.userApiRepository = userApiRepository;
    }

    @Override
    public Optional<UserApi> findById(Long userId) {
        return userApiRepository.findById(userId);
    }

    @Override
    public Optional<UserApi> findByEmail(String email) {
        return userApiRepository.findByEmail(email);
    }
}
