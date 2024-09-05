package me.romo.sgstock.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.romo.sgstock.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String publicId) throws UsernameNotFoundException {
        String result = publicId.replace(" ", "");
        return userRepository.findByPublicId(result)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + result));
    }
}
