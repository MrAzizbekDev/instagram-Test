package uz.pdp.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.instagram.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
}
