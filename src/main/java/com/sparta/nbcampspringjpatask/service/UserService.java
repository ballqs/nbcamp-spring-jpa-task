package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.component.BCryptEncryptor;
import com.sparta.nbcampspringjpatask.dto.UserInsertDto;
import com.sparta.nbcampspringjpatask.dto.UserSelectDto;
import com.sparta.nbcampspringjpatask.dto.UserSignupDto;
import com.sparta.nbcampspringjpatask.dto.UserUpdateDto;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptEncryptor bCryptEncryptor;
    private final JwtUtil jwtUtil;

    public UserSignupDto createUser(UserInsertDto userInsertDto , HttpServletResponse res) {
        String name = userInsertDto.getName();
        String email = userInsertDto.getEmail();
        // 비밀번호 암호화
        String pw = bCryptEncryptor.encode(userInsertDto.getPw());

        User user = new User(name , email , pw);

        // JWT 생성 및 헤더에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(name);
//        jwtUtil.addJwtToHeader(token , res);

        return new UserSignupDto(userRepository.save(user) , token);
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
