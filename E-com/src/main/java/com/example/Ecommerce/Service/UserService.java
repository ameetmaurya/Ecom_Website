package com.example.Ecommerce.Service;

import com.example.Ecommerce.Model.UserDtls;

import java.util.List;

public interface UserService {

    public UserDtls saveUser(UserDtls user);
    public UserDtls getUserByEmail(String email);
    public List<UserDtls> getUers(String role);

    public Boolean updateAccountStatus(Integer id, Boolean status);
}
