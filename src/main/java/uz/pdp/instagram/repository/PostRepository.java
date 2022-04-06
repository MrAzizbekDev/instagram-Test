package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Post;
import uz.pdp.instagram.entity.User;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {

  Optional<Post>findByUserAndId(User user, Integer id);

 Optional<Post> findByUserId(Integer id);
}
