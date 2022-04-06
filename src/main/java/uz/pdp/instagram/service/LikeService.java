package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.PostDto;
import uz.pdp.instagram.entity.Like;
import uz.pdp.instagram.entity.Post;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.LikeRepository;
import uz.pdp.instagram.repository.PostRepository;
import uz.pdp.instagram.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LikeService {
    final LikeRepository likeRepository;
    final UserRepository userRepository;
    final PostRepository postRepository;
    final PostService postService;
    public ApiResponse add(PostDto postDto, User user) {
        Like like = new Like();
        Post post = postRepository.getById(postDto.getPostId());
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
        postService.addLike(like.getId());
        return new ApiResponse("saved",true);
    }

    public ApiResponse delete(Integer id, User user) {
        Like like = likeRepository.getByUserAndId(user, id);
        like.setActive(false);
        likeRepository.save(like);
        return new ApiResponse("Deleted",true);
    }
}
