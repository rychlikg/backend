package com.grzegorz.rychlik.backend.controler;

import com.grzegorz.rychlik.backend.mapper.UserMapper;
import com.grzegorz.rychlik.backend.model.dto.UserDto;
import com.grzegorz.rychlik.backend.service.CompetitionService;
import com.grzegorz.rychlik.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final CompetitionService competitionService;

    @PostMapping
    public UserDto saveUser(@RequestPart UserDto user, @RequestPart(required = false) MultipartFile img) {
        return userMapper.toDto(userService.save(userMapper.toDao(user), user.getRoles(), img));
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto user) {
        return userMapper.toDto(userService.update(userMapper.toDao(user)));
    }

    @PatchMapping("/password")
    public void changeUserPassword(@RequestBody UserDto user) {
        userService.changePassword(user.getPassword());
    }

    @PatchMapping("/email")
    public void changeUserEmail(@RequestBody UserDto user) {
        userService.changeEmail(user.getEmail());
    }

    @PatchMapping("/img")
    public void changeUserImg(@RequestBody MultipartFile img) {
        userService.changeImg(img);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/current")
    public UserDto getCurrentUser() {
        UserDto userDto = userMapper.toDto(userService.getCurrentUser());
        userDto.setCompetitionsId(competitionService.getUserCompetitions(userDto.getId()));
        return userDto;
    }

    @GetMapping
    public Page<UserDto> getUserPage(@RequestParam int page, @RequestParam int size) {
        return userService.pageUser(PageRequest.of(page, size)).map(userMapper::toDto);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }

    @PatchMapping("/activate")
    public void activateUser(@RequestParam String activatedToken){
        userService.activateUser(activatedToken);
    }
}
