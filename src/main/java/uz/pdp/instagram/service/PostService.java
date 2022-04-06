package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.PostDto;
import uz.pdp.instagram.entity.Attachment;
import uz.pdp.instagram.entity.Like;
import uz.pdp.instagram.entity.Post;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.AttachmentRepository;
import uz.pdp.instagram.repository.LikeRepository;
import uz.pdp.instagram.repository.PostRepository;
import uz.pdp.instagram.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PostService {
    final PostRepository postRepository;
    final AttachmentRepository attachmentRepository;
    final UserRepository userRepository;
    final LikeRepository likeRepository;

    public ApiResponse getByUserId(User user) {
        Optional<Post> optional = postRepository.findByUserId(user.getId());
        return new ApiResponse("mana ", true, optional.get());
    }

    public ApiResponse add(PostDto postDto, User user) {
        Post post = new Post();
        Optional<Attachment> repository = attachmentRepository.findById(postDto.getPostId());
        if (!repository.isPresent()) return new ApiResponse("Attachment not found", false);
        post.setAttachment(repository.get());
        post.setUser(userRepository.getById(user.getId()));
        post.setCaption(postDto.getCaption());
        postRepository.save(post);
        return new ApiResponse("saved", true);
    }

    public ApiResponse delete(Integer id, User user) {

        Optional<Post> optional = postRepository.findByUserAndId(user, id);
        if (!optional.isPresent()) {
            return new ApiResponse("bunday post  ushbu userda topilmadi", false);
        }
        postRepository.delete(optional.get());
        return new ApiResponse("deleted", true);
    }

    public ApiResponse edit(PostDto postDto, Integer id, User user) {
        Optional<Post> optional = postRepository.findByUserAndId(user, id);
        if (!optional.isPresent()) {
            return new ApiResponse("bunday post  ushbu userda topilmadi", false);
        }
        Post post = optional.get();
        post.setCaption(postDto.getCaption());
        postRepository.save(post);
        return new ApiResponse("edited", true);
    }

    public Integer addLike(Integer id) {
        Post post = postRepository.getById(id);
        List<Like> likeList = likeRepository.findByActiveTrue();
        post.setCountLike(likeList.size());
        postRepository.save(post);
        return post.getCountLike();
    }
}
