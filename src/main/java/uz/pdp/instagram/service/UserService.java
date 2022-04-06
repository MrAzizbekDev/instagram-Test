package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.RegisterDto;
import uz.pdp.instagram.entity.Attachment;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.AttachmentRepository;
import uz.pdp.instagram.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
//    final UserRepository userRepository;
//    final AttachmentRepository attachmentRepository;
//
//    public ApiResponse register(RegisterDto registerDto) {
//        if (userRepository.existsByEmailOrPhone(registerDto.getEmail(), registerDto.getPhone()))
//            return new ApiResponse("Bunday User mavjud", false);
//        User user = new User();
//        user.setFullName(registerDto.getFullName());
//        user.setEmail(registerDto.getEmail());
//        if (registerDto.getPassword().equals(registerDto.getPrePassword()))
//            return new ApiResponse("PrePassword and password not equals",false);
//        user.setPassword(registerDto.getPassword());
//
//        user.setPhone(registerDto.getPhone());
//        Attachment attachment = attachmentRepository.getById(registerDto.getAttachmentId());
//        user.setAttachment(attachment);
//        return new ApiResponse("Saved", true);
//    }

}
