package com.example.Ecommerce.Repository;

import com.example.Ecommerce.Model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls,Integer> {

  public UserDtls findByEmail(String email);
}
