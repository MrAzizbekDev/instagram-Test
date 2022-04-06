package uz.pdp.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.instagram.aop.CurrentUser;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.LoginDTO;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.UserRepository;
import uz.pdp.instagram.security.JwtProvider;
import uz.pdp.instagram.service.AuthService;
import uz.pdp.instagram.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final JwtProvider jwtProvider;
    final AuthService authService;
//    final EmailConfig emailConfig;
    final UserService userService;
    final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    int i;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        //login qiladi Tizimda bor bo'lsa token generate qilishimz kerak

        ApiResponse responce;

        if (Objects.isNull(loginDTO.getName()) || Objects.isNull(loginDTO.getPassword())) {
            return ResponseEntity.badRequest().body("fields cannot be empty");
        }

        if (loginDTO.getName().endsWith("@gmail.com")) {
            responce = authService.loadUserByEmail(loginDTO.getName(), (loginDTO.getPassword()));
        } else if (loginDTO.getName().length() == 13 && loginDTO.getName().charAt(0) == '+') {
            responce = authService.loadUserByPhone(loginDTO.getName(), loginDTO.getPassword());
        } else {
            responce = authService.loadUserByUsername(loginDTO.getName(), (loginDTO.getPassword()));
        }


      if (!responce.getSuccess()) {
           return ResponseEntity.badRequest().body(new ApiResponse("bad credentials", false));
      }

        //UserDetails userDetails = authService.loadUserByUsername(loginDTO.getName());
        String token = jwtProvider.generateToken(loginDTO.getName());

        System.out.println(responce);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@CurrentUser User user) {
        System.out.println(user);
        return ResponseEntity.ok().body(user);

    }


//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody SignUpDTO signUpDTO) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        i = (int) ((Math.random() + 1) * 1000000);
//        message.setText("Hi,\n" +
//                "\n" +
//                "Someone tried to sign up for an Instagram account with " + signUpDTO.getGmail() + ". If it was you, enter this confirmation code in the app: " + i);
//        message.setSubject("GO GO");
//        message.setSentDate(new Date());
//        message.setTo(signUpDTO.getGmail());
//        JavaMailSender mailSender = emailConfig.send();
//        mailSender.send(message);
//
//        System.out.println("\n\n\n\n\n" + i);
//
//        User user = new User();
//        user.setEmail(signUpDTO.getGmail());
//        user.setCode(i);
//        user.isEnabled(false);
//        userRepository.save(user);
//
//        return ResponseEntity.ok().body(new ApiResponse("Mana", true, signUpDTO));
//    }
//
//    @GetMapping("/verify/")
//    public ResponseEntity<?> verify(@RequestBody UserDTO userDTO) {
//
//
//            return ResponseEntity.ok().body(new ApiResponse("code is true", true));
//
//        if (i == id) {
//            User user = new User();
//            user.setEmail(signUpDTO.getGmail());
//            user.setUserName(signUpDTO.getUserName());
//            user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
//            userRepository.save(user);
//
//            System.out.println("\n\n\n\n\n" + i);
//        }
//        return ResponseEntity.status(405).body("This code is faled");
    }


//    //400 xatolik bo'lganda  aynan shu(MethodArgumentNotValidException) toifali xatolikni ushlaydi
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

