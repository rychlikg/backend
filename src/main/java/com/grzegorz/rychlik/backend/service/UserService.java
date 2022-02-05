package com.grzegorz.rychlik.backend.service;

import com.grzegorz.rychlik.backend.config.properties.FilePropertiesConfig;
import com.grzegorz.rychlik.backend.model.dao.User;
import com.grzegorz.rychlik.backend.repository.RoleRepository;
import com.grzegorz.rychlik.backend.repository.UserRepository;
import com.grzegorz.rychlik.backend.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FilePropertiesConfig filePropertiesConfig;
    private final MailService mailService;
    @Value("${url.server}")
    private String serviceUrl;

    @SneakyThrows
    public User save(User user, List<String> roles, MultipartFile img) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByNameIn(roles));
        user.setActivatedToken(UUID.randomUUID().toString());
        userRepository.save(user);
        mailService.sendEmail(Collections.singletonMap("link", serviceUrl + "/activate?token=" + user.getActivatedToken()), "confirmationRegistration", user.getEmail());
        if (img != null) {
            String fileName = user.getId() + ".png";
            Path path = Paths.get(filePropertiesConfig.getLogo(), fileName);
            Files.copy(img.getInputStream(), path);
            user.setImgPath("/image/" + fileName);
        } else {
            user.setImgPath("/image/" + "defaultImg.png");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        User userDb = getCurrentUser();
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        return userDb;
    }

    @Transactional
    public void changePassword(String newPassword) {
        User currentUser = getCurrentUser();
        currentUser.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void changeEmail(String email) {
        User currentUser = getCurrentUser();
        currentUser.setEmail(email);
    }

    @SneakyThrows
    @Transactional
    public void changeImg(MultipartFile img) {
        User currentUser = getCurrentUser();
        String fileName = currentUser.getId() + ".png";
        Path path = Paths.get(filePropertiesConfig.getLogo(), fileName);
        Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        currentUser.setImgPath("/image/" + fileName);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(SecurityUtils.getCurrentUserEmail()).orElseThrow(EntityNotFoundException::new);
    }

    public Page<User> pageUser(Pageable pageable) {
        return userRepository.findByRoles_Name("ROLE_USER", pageable);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Transactional
    public void activateUser(String activatedToken){
        userRepository.findByActivatedToken(activatedToken).ifPresent(user -> user.setActivatedToken(null));
    }

    public List<User> findByFirstNameOrLastName (String name){
        return userRepository.findTop10ByFirstNameContainsOrLastNameContains(name,name);
    }
}
