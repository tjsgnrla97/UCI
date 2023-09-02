package com.cos.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cos.jwt.model.RedisLogout;
import com.cos.jwt.model.RoomHistory;
import com.cos.jwt.model.User;
import com.cos.jwt.model.UserImg;
import com.cos.jwt.repository.RedisLogoutRepository;
import com.cos.jwt.repository.RoomHistoryRepository;
import com.cos.jwt.repository.UserImgRepository;
import com.cos.jwt.repository.UserRepository;
import com.cos.jwt.request.UserJoinRequest;
import com.cos.jwt.request.UserModifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserImgRepository userImgRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RedisLogoutRepository redisLogoutRepository;
    @Autowired
    RoomHistoryRepository roomHistoryRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserImgRepository userImgRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RedisLogoutRepository redisLogoutRepository) {
        this.userRepository = userRepository;
        this.userImgRepository = userImgRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.redisLogoutRepository = redisLogoutRepository;
    }

    public User joinUser(UserJoinRequest userJoinRequest) {
        User user = new User();
        user.setEmail(userJoinRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userJoinRequest.getPassword()));
        user.setDepartment(userJoinRequest.getDepartment());
        user.setNickname(userJoinRequest.getNickname());
        user.setPosition(userJoinRequest.getPosition());
        user.setUsername(userJoinRequest.getUsername());
        user.setRoles("ROLE_USER");

        return userRepository.save(user);
    }

//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles("ROLE_USER");
//        userRepository.save(user);

    public User getUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        return user;
    }

    public Optional<RedisLogout> getRedisLogoutByToken(String token) {
        String modifyToken = token.substring(7);
        Optional<RedisLogout> redisLogout = redisLogoutRepository.findByToken(modifyToken);
        return redisLogout;
    }


    public User modifyUser(UserModifyRequest userModifyRequest, String email) {
        User userUpdate = userRepository.findByEmail(email);
        userUpdate.setEmail(userModifyRequest.getEmail());
        userUpdate.setUsername(userModifyRequest.getUsername());
        userUpdate.setPosition(userModifyRequest.getPosition());
        userUpdate.setDepartment(userModifyRequest.getDepartment());

        return userRepository.save(userUpdate);
    }

//    public UserImg registerUserImg(UserImgRequest userImgRequest) {
//
//        UserImg userImg = new UserImg();
//        userImg.setOriginFile(userImgRequest.getOriginFile());
//        userImg.setSaveFile(userImgRequest.getSaveFile());
//        userImg.setFilePath(userImgRequest.getFilePath());
//
//
//        userImgRepository.save(userImg);
//        return userImg;
//
//
//    }

    public void fileUpload(UserImg files, Long seq) {
        Optional<User> user = userRepository.findBySeq(seq);
        System.out.println(user.toString());
        if(userImgRepository.findByUser(user.get()).isPresent()){
            System.out.println("수정");
            Optional<UserImg> update = userImgRepository.findByUser(user.get());
            UserImg userImgUpdate = update.get();
            userImgUpdate.setOriginFile(files.getOriginFile());
            userImgUpdate.setSaveFolder(files.getSaveFolder());
            userImgUpdate.setSaveFile(files.getSaveFile());
            userImgUpdate.setUser(user.get());
            userImgRepository.save(userImgUpdate);
        } else {
            System.out.println("추가");
            files.setUser(user.get());
            userImgRepository.save(files);
        }
    }

    public UserImg getUserImg(Long userSeq) {
        User user = userRepository.findBySeq(userSeq).get();
        UserImg userImg = userImgRepository.findByUser(user).get();
        return userImg;
    }

    public List<RoomHistory> searchRoomHistoryList(Long userSeq) {
        User user = userRepository.findBySeq(userSeq).get();
        List<RoomHistory> roomHistoryList = roomHistoryRepository.findByUser(user);
        return roomHistoryList;
    }


    public RedisLogout saveRedisLogout(String data) {
        //앞에 bearer 삭제한 only 토큰 값

        String jwtToken = data.substring(7);
//        System.out.println("내가만든 jwtToken : " + jwtToken);
        String email =
                JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken).getClaim("email").asString();
//        System.out.println("email은 : " + email);

        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        Long expirationTime = decodedJWT.getExpiresAt().getTime();
//        System.out.println(decodedJWT.getExpiresAt());
        Long now = new Date().getTime();
        Long remainingTime = expirationTime - now;
//        System.out.println(remainingTime);

        RedisLogout redis = new RedisLogout();
        redis.setToken(jwtToken);
//        System.out.println("jwtToken: "+ jwtToken);
        redis.setLogout("Logout");
//        System.out.println("remainigTime/1000L : "+remainingTime/1000L);
        redis.setExpired(remainingTime/1000L);



        return redisLogoutRepository.save(redis);
    }


//    public void save(UserImg userImg) {
//        UserImg img = new UserImg();
//        img.
//    }
}
