package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Comment;
import uz.pdp.instagram.entity.User;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

}
