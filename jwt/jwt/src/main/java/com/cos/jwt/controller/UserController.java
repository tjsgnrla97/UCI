package com.cos.jwt.controller;

import com.cos.jwt.model.*;
import com.cos.jwt.repository.UserImgRepository;
import com.cos.jwt.repository.UserRepository;
//import com.cos.jwt.service.UserService;
import com.cos.jwt.request.UserImgRequest;
import com.cos.jwt.request.UserJoinRequest;
import com.cos.jwt.request.UserModifyRequest;
import com.cos.jwt.response.UserResponse;
import com.cos.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.xml.ws.Response;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    ServletContext servletContext;

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    //private final UserService userService;

    @GetMapping("home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("token")
    public String token() {
        return"<h1>token</h1>";
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserJoinRequest userJoinRequest) {

        User user = userService.joinUser(userJoinRequest);


//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles("ROLE_USER");
//        userRepository.save(user);


        return new ResponseEntity<>(HttpStatus.OK);
    }

    //로그아웃
    @GetMapping(path = "/logout" , headers ="Authorization")
    public void logout(@RequestHeader("Authorization") String data) {

        System.out.println("실행됨");
        System.out.println("데이터는"+data);
        RedisLogout redisLogout = userService.saveRedisLogout(data);


    }

    //내 프로필 조회
    @GetMapping("/me/{email}")
    public ResponseEntity getUserProfile(@PathVariable("email") String email) {

        User user = userService.getUserByUserEmail(email);

        if (user.getEmail() == null) {
            return ResponseEntity.status(404).body("존재하지 않는 페이지 입니다");

        } else {
            UserResponse res = new UserResponse();
            res.setEmail(user.getEmail());
            res.setDepartment(user.getDepartment());
            res.setPosition(user.getPosition());
            res.setUsername(user.getUsername());
            res.setSeq(user.getSeq());
            res.setNickname(user.getNickname());

            return ResponseEntity.status(200).body(res);
        }
    }

    //프로필에서 정보 수정
    @PutMapping("me/{email}")
    public ResponseEntity<?> modifyUser(@PathVariable("email") String email, @RequestBody UserModifyRequest userModifyRequest) {
        User user = userService.modifyUser(userModifyRequest,email);

        return ResponseEntity.status(200).body("ok");
    }


    @PostMapping("me/imgurl/{userSeq}")
    public ResponseEntity fileUpload(@RequestParam("files") MultipartFile files, @PathVariable("userSeq") Long userSeq) throws IOException {
        String realPath = servletContext.getRealPath("/upload");
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = "C:/ssafy/"+today;

        System.out.printf("저장폴더 = " + saveFolder);
        File folder = new File(saveFolder);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        UserImg userImg = new UserImg();
        String originalFileName = files.getOriginalFilename();
        if(!originalFileName.isEmpty()) {
            String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));

            userImg.setSaveFolder(today);
            userImg.setOriginFile(originalFileName);
            userImg.setSaveFile(saveFileName);
            files.transferTo(new File(folder, saveFileName));
        }

        userService.fileUpload(userImg, userSeq);

        return ResponseEntity.status(201).body("ok");

    }

//    @GetMapping("me/imgurl/{userSeq}")
//    public Resource getUserProfileImg(@PathVariable("userSeq") Long userSeq) throws MalformedURLException {
//        UserImg userImg = userService.getUserImg(userSeq);
//        String url = "C:\\ssafy\\"+userImg.getSaveFolder()+"\\"+userImg.getSaveFile();
//        System.out.println(url);
//        return new UrlResource("file:///C:\\ssafy\\"+userImg.getSaveFolder()+"\\"+userImg.getSaveFile());
//    }


    // 리턴 string으로 감
    @GetMapping("me/imgurl/{userSeq}")
    public ResponseEntity getUserProfileImg(@PathVariable("userSeq") Long userSeq) {
        UserImg userImg = userService.getUserImg(userSeq);
        String url = "C:\\ssafy\\"+userImg.getSaveFolder()+"\\"+userImg.getSaveFile();
        return new ResponseEntity<>(url,HttpStatus.OK);
    }

    //해당 유저 seq로 회의이력 조회
    @GetMapping("history/{userSeq}")
    public @ResponseBody List<RoomHistory> findByUserSeqHistory(@PathVariable("userSeq") Long userSeq){
        List<RoomHistory> roomHistoryList = userService.searchRoomHistoryList(userSeq);
        return roomHistoryList;
    }



    //user,manager,admin 권한 접근 가능
    @GetMapping("api/v1/user")
    public String user() {
        return "user";
    }

    //admin 권한만 접근 가능
    @GetMapping("api/v1/admin")
    public String admin() {
        return "admin";
    }
    

}
