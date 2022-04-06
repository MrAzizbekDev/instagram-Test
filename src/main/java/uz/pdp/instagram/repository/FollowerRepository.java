package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Follower;
import uz.pdp.instagram.entity.Following;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower,Integer> {
   Optional<Following>findByUserId(Integer userId);

}
