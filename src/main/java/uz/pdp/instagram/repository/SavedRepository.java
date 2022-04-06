package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Comment;
import uz.pdp.instagram.entity.Saved;

public interface SavedRepository extends JpaRepository<Saved,Integer> {

}
