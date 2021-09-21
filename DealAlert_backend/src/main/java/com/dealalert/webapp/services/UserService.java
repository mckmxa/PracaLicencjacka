package com.dealalert.webapp.services;

import com.dealalert.webapp.exceptions.UserNotFoundException;
import com.dealalert.webapp.models.User;
import com.dealalert.webapp.repository.UserRepository;
import com.dealalert.webapp.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;

    public UserService(UserRepository userRepository, WishListRepository wishListRepository) {
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public void deleteUser(String id){
        Optional<User> user = userRepository.findUserById(id);
        user.ifPresent(value -> wishListRepository.deleteAllByUsername(value.getUsername()));
        userRepository.deleteUserById(id);
    }
}
