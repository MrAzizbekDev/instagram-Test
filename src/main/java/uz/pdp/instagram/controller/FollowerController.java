package uz.pdp.instagram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.instagram.aop.CurrentUser;
import uz.pdp.instagram.dto.ApiResponse;
import uz.pdp.instagram.entity.User;
import uz.pdp.instagram.service.FollowerService;

@RestController
@RequestMapping("/api/follower")
@RequiredArgsConstructor
public class FollowerController {
    final FollowerService followerService;
    @PostMapping("/add/{id}")
    public ResponseEntity add(@CurrentUser User user,@PathVariable Integer id){
        ApiResponse apiResponse=followerService.add(user,id);
        return ResponseEntity.ok().body(apiResponse);
    }
    @GetMapping("/allByUser")
    public ResponseEntity getAllByUser(@CurrentUser User user){
        ApiResponse apiResponse=followerService.getAll(user);
        return ResponseEntity.status(apiResponse.getSuccess()? 200:409).body(apiResponse);
    }
    @PostMapping("/ignore/{id}")
     public ResponseEntity ignore(@PathVariable Integer id,@CurrentUser User user){
        ApiResponse apiResponse=followerService.ignore(id,user);
        return ResponseEntity.ok().body(apiResponse);
    }
}
