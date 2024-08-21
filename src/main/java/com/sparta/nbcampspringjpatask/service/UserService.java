package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.dto.*;
import com.sparta.nbcampspringjpatask.entity.Comment;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSelectDto createUser(UserInsertDto userInsertDto) {
        User user = new User(userInsertDto);
        return new UserSelectDto(userRepository.save(user));
    }

    public UserSelectDto selectUser(Long id) {
        return new UserSelectDto(findById(id));
    }

    public List<UserSelectDto> selectAllUser() {
        return userRepository.findAll().stream().map(UserSelectDto::new).toList();
    }

    @Transactional
    public UserSelectDto updateUser(Long id , UserUpdateDto userUpdateDto) {
        User user = findById(id);
        user.update(userUpdateDto);
        return new UserSelectDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 유저는 존재하지 않습니다."));
    }
}
