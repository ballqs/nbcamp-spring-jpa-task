package com.sparta.nbcampspringjpatask.service;

import com.sparta.nbcampspringjpatask.component.BCryptEncryptor;
import com.sparta.nbcampspringjpatask.dto.*;
import com.sparta.nbcampspringjpatask.entity.User;
import com.sparta.nbcampspringjpatask.entity.UserRoleEnum;
import com.sparta.nbcampspringjpatask.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
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

    public UserSignupResponseDto createUser(UserInsertDto userInsertDto , HttpServletResponse res) {
        String name = userInsertDto.getName();
        String email = userInsertDto.getEmail();

        Optional<User> checkUserName = userRepository.findByName(name);
        if (checkUserName.isPresent()) {
            throw new DuplicateKeyException("중복된 사용자가 존재합니다.");
        }

        Optional<User> checkUserEmail = userRepository.findByEmail(email);
        if (checkUserEmail.isPresent()) {
            throw new DuplicateKeyException("중복된 Email입니다.");
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

    public UserSelectDto selectUser(Long id) {
        return new UserSelectDto(findById(id));
    }

    @Transactional(readOnly = true)
    public List<UserSelectDto> selectAllUser() {
        return userRepository.findAll().stream().map(UserSelectDto::new).toList();
    }

    public UserSelectDto updateUser(Long id , UserUpdateDto userUpdateDto) {
        User user = findById(id);
        user.update(userUpdateDto);
        return new UserSelectDto(user);
    }

    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userLoginRequestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보입니다.")));

        if (!bCryptEncryptor.matches(userLoginRequestDto.getPw() , user.get().getPw())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        String token = jwtUtil.createToken(user.get().getEmail() , user.get().getRole());
        return new UserLoginResponseDto(token);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 유저는 존재하지 않습니다."));
    }
}
