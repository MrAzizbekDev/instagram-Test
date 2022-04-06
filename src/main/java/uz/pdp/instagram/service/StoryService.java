package uz.pdp.instagram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.PostDto;
import uz.pdp.instagram.entity.Attachment;
import uz.pdp.instagram.entity.Story;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.AttachmentRepository;
import uz.pdp.instagram.repository.StoryRepository;
import uz.pdp.instagram.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StoryService {
    final StoryRepository storyRepository;
    final AttachmentRepository attachmentRepository;
    final UserRepository userRepository;

    public ApiResponse getByUserId(Integer id, User user) {

        List<Story> list = storyRepository.getAllByUserAndId(user, id);
        return new ApiResponse("mana ", true,list);
    }

    public ApiResponse add(PostDto postDto, User user) {
        Story story = new Story();
        Optional<Attachment> repository = attachmentRepository.findById(postDto.getPostId());
        if (!repository.isPresent()) return new ApiResponse("Attachment not found", false);
        story.setAttachment(repository.get());
        story.setUser(user);
        storyRepository.save(story);
        return new ApiResponse("saved", true);
    }

    public ApiResponse delete(Integer id, User user) {

        storyRepository.deleteByUserAndId(user,id);
        return new ApiResponse("deleted", true);
    }


}
