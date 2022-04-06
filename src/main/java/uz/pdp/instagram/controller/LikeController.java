package uz.pdp.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.instagram.aop.CurrentUser;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.dto.PostDto;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.service.LikeService;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {
    final LikeService likeService;
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody PostDto postDto, @CurrentUser User user){
       ApiResponse apiResponse= likeService.add(postDto,user);
       return ResponseEntity.ok().body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id,@CurrentUser User user){
       ApiResponse apiResponse= likeService.delete(id,user);
       return ResponseEntity.ok().body(apiResponse);
    }


}
