package uz.pdp.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.instagram.aop.CurrentUser;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.PostDto;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.repository.StoryRepository;
import uz.pdp.instagram.service.PostService;
import uz.pdp.instagram.service.StoryService;

@RestController
@RequestMapping("/api/story")
@RequiredArgsConstructor
public class StoryController {

    final StoryService storyService;

    @GetMapping("/{userId}")
    public ResponseEntity getByUserId(@CurrentUser User user,@PathVariable Integer id) {
        ApiResponse apiResponse = storyService.getByUserId(id,user);
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody PostDto postDto,@CurrentUser User user){
        ApiResponse apiResponse=storyService.add(postDto,user);
        return ResponseEntity.status(apiResponse.getSuccess()? 201:404).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id,@CurrentUser User user){
        ApiResponse apiResponse=storyService.delete(id,user);
        return ResponseEntity.ok().body(apiResponse);
    }
}
