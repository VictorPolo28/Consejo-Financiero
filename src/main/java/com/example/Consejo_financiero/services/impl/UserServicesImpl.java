package com.example.Consejo_financiero.services.impl;

import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.entity.UsersStatus;
import com.example.Consejo_financiero.repository.UsuerRepository;
import com.example.Consejo_financiero.services.UsersService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UsersService {



    @Autowired
    private UsuerRepository userRepository;

    @Override
    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public List<Users> getByStatus(UsersStatus usersStatus) {
        return List.of();
    }


    @Override
    public Optional<Users> searchById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<Users> searchByNameUser(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public Optional<Users> searchByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public Users updateUserInformation(Long userId, Users user) {
        return null;
    }

    @Override
    public Users changeUserStatus(Long userId, UsersStatus newUserStatus) {
        return null;
    }


}



