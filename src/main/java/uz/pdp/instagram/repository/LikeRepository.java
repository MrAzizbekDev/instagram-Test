package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.instagram.entity.Comment;
import uz.pdp.instagram.entity.Like;
import uz.pdp.instagram.entity.User;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Integer> {
  @Query("select l from likes l where l.active = true")
  List<Like> findByActiveTrue();
  Like deleteByUserAndId(User user, Integer id);
  Like getByUserAndId(User user, Integer id);
}
