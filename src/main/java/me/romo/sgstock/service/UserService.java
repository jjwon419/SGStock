package me.romo.sgstock.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.romo.sgstock.domain.User;
import me.romo.sgstock.dto.AddUserRequest;
import me.romo.sgstock.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        return userRepository.save(
                User.builder()
                        .publicId(dto.getPublicId().replaceAll(" ", ""))
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                        .money(10000000)
                        .build()).getId();
    }

    public boolean existsByPublicId(String publicId) {
        return userRepository.existsByPublicId(publicId);
    }
}
