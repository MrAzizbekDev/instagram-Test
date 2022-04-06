package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.entity.Follower;
import uz.pdp.instagram.entity.Following;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.FollowerRepository;
import uz.pdp.instagram.repository.FollowingRepository;
import uz.pdp.instagram.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FollowingService {
    final UserRepository userRepository;
    final FollowingRepository followingRepository;

    public ApiResponse add(User user, Integer id) {
        Following following = new Following();
        following.setUserId(id);
        following.setFollowingUsers(Collections.singletonList(user));
        followingRepository.save(following);
        return new ApiResponse("Saved", true);
    }

    public ApiResponse getAll(User user) {
        Optional<Following> optional = followingRepository.findByUserId(user.getId());
        boolean empty = !optional.isPresent();
        return new ApiResponse(empty ? "Not Found" : "mana", true, optional.get());
    }

    public ApiResponse ignore(Integer id, User user) {

        User user1 = userRepository.getById(id);
        Following following = followingRepository.getById(user1.getId());
        List<User> followerUser = following.getFollowingUsers();
        followerUser.remove(user);
        following.setFollowingUsers(followerUser);
        followingRepository.save(following);
        return new ApiResponse("Ochirildi",true);

    }
}
