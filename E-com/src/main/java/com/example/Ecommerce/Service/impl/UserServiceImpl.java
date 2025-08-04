package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Model.UserDtls;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        Optional<UserDtls> findByuser=userRepository.findById(id);
        if(findByuser.isPresent())
        {

            UserDtls userDtls =findByuser.get();
            userDtls.setEnable(status);
            userRepository.save(userDtls);
            return true;
        }
        return false;
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDtls> getUers(String role) {
        return userRepository.findByRole(role);

    }


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDtls saveUser(UserDtls user){
        user.setRole("ROLE_USER");
        user.setEnable(true);
       String encodePassword = passwordEncoder.encode(user.getPassword());
       user.setPassword(encodePassword);
        UserDtls saveUser =userRepository.save(user);
        return saveUser;
    }
}
