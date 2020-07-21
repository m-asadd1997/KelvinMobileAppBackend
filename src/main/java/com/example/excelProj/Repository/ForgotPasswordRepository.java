package com.example.excelProj.Repository;

import com.example.excelProj.Model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Long> {
    public ForgotPassword findByToken(String token);

}
