package com.study.user.service;

import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.entity.User;
import com.study.user.exception.NotFoundException;
import com.study.user.mapper.UserMapper;
import com.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final String MESSAGE_KEY_HEAD = "user.user.";

    public List<UserDTO> findAll(){
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO findById(final String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_user.error"));
        return userMapper.toDTO(user);
    }

    public ResponseDTO registerUser(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());
        if(optionalUser.isPresent()){
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "id_exist.error");
        } else {
            BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
            userDTO.setUserPw(pwEncoder.encode(userDTO.getUserPw()));
            userRepository.save(userMapper.toEntity(userDTO));
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "register.success");
        }
    }

    public ResponseDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_user.error"));
        userRepository.save(userMapper.toEntity(userDTO));
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "update.success");
    }

    public ResponseDTO deleteUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_user.error"));
        user.softDelete();
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "delete.success");
    }

    public ResponseDTO changePassword(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_user.error"));
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        user.changePassword(pwEncoder.encode(userDTO.getUserPw()));
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "change_pw.success");
    }
}
