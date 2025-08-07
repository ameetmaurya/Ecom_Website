package com.example.Ecommerce.Service.impl;

import com.example.Ecommerce.Model.UserDtls;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.Service.UserService;
import com.example.Ecommerce.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void increaseFailedAttempt() {

    }

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

    @Override
    public void increaseFailedAttempt(UserDtls user) {
        int attempt=  user.getFailedAttempt()+1;
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    @Override
    public void userAccountLock(UserDtls user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public boolean unlockAccountTimeExpired(UserDtls user) {

       long lockTime= user.getLockTime().getTime();
       long unlockTime=lockTime+ AppConstant.Unlock_Duration_Time;
       long currentTime=System.currentTimeMillis();

       if(unlockTime<currentTime)
       {
           user.setAccountNonLocked(true);
           user.setFailedAttempt(0);
           user.setLockTime(null);
           userRepository.save(user);
           return true;
       }
        return false;
    }

    @Override
    public void resetAttempt(int userId) {

    }
}
