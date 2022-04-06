package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.entity.Follower;
import uz.pdp.instagram.entity.Following;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.FollowerRepository;
import uz.pdp.instagram.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FollowerService {
    final UserRepository userRepository;
    final FollowerRepository followerRepository;

    public ApiResponse add(User user, Integer id) {
        Follower follower = new Follower();
        follower.setUserId(id);
        follower.setFollowerUser(Collections.singletonList(user));
        followerRepository.save(follower);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse getAll(User user) {
        Optional<Following> optional = followerRepository.findByUserId(user.getId());
        boolean empty = !optional.isPresent();
        return new ApiResponse(empty ? "Not Found" : "mana", true, optional.get());

    }

    public ApiResponse ignore(Integer id, User user) {

        User user1 = userRepository.getById(id);
        Follower follower = followerRepository.getById(user1.getId());
        List<User> followerUser = follower.getFollowerUser();
        followerUser.remove(user);
        follower.setFollowerUser(followerUser);
        followerRepository.save(follower);
        return new ApiResponse("Ochirildi",true);

    }
}
