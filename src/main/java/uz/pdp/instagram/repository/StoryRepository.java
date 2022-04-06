package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Post;
import uz.pdp.instagram.entity.Story;
import uz.pdp.instagram.entity.User;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Integer> {
   List<Story>getAllByUserAndId(User user, Integer id);
   Post deleteByUserAndId(User user, Integer id);
}
