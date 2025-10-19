package com.example.Consejo_financiero.services;

import com.example.Consejo_financiero.entity.Users;
import com.example.Consejo_financiero.entity.UsersStatus;

import java.util.List;
import java.util.Optional;

public interface UsersService {




    Optional<Users> searchById(Long userId);
    Optional<Users> searchByNameUser(String userName);
    Optional<Users> searchByEmail(String userEmail);

    Users updateUserInformation(Long userId, Users user);
    Users changeUserStatus(Long userId, UsersStatus newUserStatus);
    Users registerUser(Users user);


    List<Users> getByStatus(UsersStatus usersStatus);
}