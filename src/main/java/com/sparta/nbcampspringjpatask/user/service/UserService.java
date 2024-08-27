package com.sparta.nbcampspringjpatask.user.service;

import com.sparta.nbcampspringjpatask.support.BCryptEncryptor;
import com.sparta.nbcampspringjpatask.user.entity.User;
import com.sparta.nbcampspringjpatask.user.entity.UserRoleEnum;
import com.sparta.nbcampspringjpatask.common.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.user.repository.UserRepository;
import com.sparta.nbcampspringjpatask.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptEncryptor bCryptEncryptor;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public UserSignupResponseDto createUser(UserInsertDto userInsertDto) {
        String name = userInsertDto.getName();
        String email = userInsertDto.getEmail();

        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 Email입니다.");
        }

        // 비밀번호 암호화
        String pw = bCryptEncryptor.encode(userInsertDto.getPw());

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userInsertDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userInsertDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(name , email , pw , role);

        // JWT 생성 및 헤더에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(name , role);

        return new UserSignupResponseDto(userRepository.save(user) , token);
    }

    public UserSelectDto getUser(Long id) {
        return new UserSelectDto(userRepository.findByIdOrElseThrow(id));
    }

    @Transactional(readOnly = true)
    public List<UserSelectDto> getUsers() {
        return userRepository.findAll().stream().map(UserSelectDto::new).toList();
    }

    public UserSelectDto updateUser(Long id , UserUpdateDto userUpdateDto) {
        User user = userRepository.findByIdOrElseThrow(id);
        user.update(userUpdateDto.getName());
        return new UserSelectDto(user);
    }

    public void removeUser(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        userRepository.delete(user);
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userLoginRequestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("이메일 혹은 비밀번호가 맞지 않습니다.")));

        if (!bCryptEncryptor.matches(userLoginRequestDto.getPw() , user.get().getPw())) {
            throw new IllegalArgumentException("이메일 혹은 비밀번호가 맞지 않습니다.");
        }

        String token = jwtUtil.createToken(user.get().getEmail() , user.get().getRole());
        return new UserLoginResponseDto(token);
    }

    @Transactional(readOnly = true)
    public User getUserEntity(Long id) {
        return userRepository.findByIdOrElseThrow(id);
    }
}
