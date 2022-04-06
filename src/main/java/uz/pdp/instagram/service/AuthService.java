package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;


    public ApiResponse loadUserByUsername(String username, String password)  {
        Optional<User> byUsername = userRepository.findByUserName(username);

        if(!byUsername.isPresent())
            return new ApiResponse("User not found !", false);

        if (!passwordEncoder.matches(password, byUsername.get().getPassword()))
            return new ApiResponse("Bad credential !", false);

        return  new ApiResponse("loaded", true, byUsername.get());

    }
    public ApiResponse loadUserByEmail(String email, String password) {
        Optional<User> byEmail = userRepository.findByEmail(email);

        if(!byEmail.isPresent())
            return new ApiResponse("User not found !", false);

        if (!passwordEncoder.matches(password, byEmail.get().getPassword()))
            return new ApiResponse("Bad credential !", false);

        return  new ApiResponse("loaded", true, byEmail.get());

    }

    public ApiResponse loadUserByPhone(String phone, String password) {
        Optional<User> byPhone = userRepository.findByPhone(phone);

        if(!byPhone.isPresent())
            return new ApiResponse("User not found !", false);

        if (!passwordEncoder.matches(password, byPhone.get().getPassword()))
            return new ApiResponse("Bad credential !", false);

        return  new ApiResponse("loaded", true, byPhone.get());

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUserName(username);

        System.out.println(byUsername);
        return (UserDetails) byUsername.orElseThrow(() -> new UsernameNotFoundException("User topilmadi!"));

    }
}
