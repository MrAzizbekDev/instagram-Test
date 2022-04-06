package uz.pdp.instagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.instagram.entity.Attachment;
import uz.pdp.instagram.entity.AttachmentContent;
import uz.pdp.instagram.repository.AttachmentContentRepository;
import uz.pdp.instagram.repository.AttachmentRepository;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {
    public static final String upload="yuklanganlar";
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository contentRepository;
    @PostMapping("/upload")
    public ResponseEntity uploadFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file !=null){
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setContentType(contentType);
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            Attachment saveAttachment = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(saveAttachment);
            AttachmentContent save = contentRepository.save(attachmentContent);
            return ResponseEntity.ok().body(saveAttachment.getId());

        }
        return ResponseEntity.status(404).body("ERROR");
    }
    @GetMapping("/download/{id}")
    public ResponseEntity downloadFile(@PathVariable Integer id,
                             HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = contentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition","attachment; filename\""
                        +attachment.getFileOriginalName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
                return ResponseEntity.ok().body(response);
            }
        }
        return ResponseEntity.status(404).body("Not found");
    }
    @GetMapping("/info")
    public ResponseEntity get(){
        return ResponseEntity.ok().body(attachmentRepository.findAll());
    }

    @PostMapping("/uploadSystem")
    public String upload(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
       if (file !=null){
           String originalFilename = file.getOriginalFilename();
           Attachment attachment = new Attachment();
           attachment.setFileOriginalName(originalFilename);
           attachment.setSize(file.getSize());
           String[] split = originalFilename.split("\\.");
           String name = UUID.randomUUID().toString()+"."+split[split.length-1];
           attachment.setName(name);
           attachment.setContentType(split[split.length-1]);
           attachmentRepository.save(attachment);
          Path path= Paths.get(upload+"/"+name);
           Files.copy(file.getInputStream(),path);
           return "Attachment saqlandi, idsi"+attachment.getId();
       }
       return "saqlanmadi";
    }
    @GetMapping("/downloadSystem/{id}")
    public void gett(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment=attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition","attachment; filename\""
                    +attachment.getFileOriginalName()+"\"");
            response.setContentType(attachment.getContentType());
            response.setContentType(attachment.getContentType());
            FileInputStream fileInputStream=new FileInputStream( upload+"/"+attachment.getName());
            FileCopyUtils.copy(fileInputStream,response.getOutputStream());
        }
    }
    @GetMapping("/attachment")
    public List<Attachment> getAll(){
        return attachmentRepository.findAll();
    }

}