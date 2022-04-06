package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Following;
import uz.pdp.instagram.entity.User;

import java.util.Optional;

public interface FollowingRepository extends JpaRepository<Following,Integer> {
   Optional<Following>findByUserId(Integer userId);
}
