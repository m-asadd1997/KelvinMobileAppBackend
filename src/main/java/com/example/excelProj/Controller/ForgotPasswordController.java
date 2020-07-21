package com.example.excelProj.Controller;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.ForgotPasswordDto;
import com.example.excelProj.Service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/forgotpassword")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/mail-to-user")
    public ApiResponse getPasswordForEmpAndSup(@RequestBody ForgotPasswordDto forgotPasswordDTO){
        return forgotPasswordService.SendMailToUserAndSave(forgotPasswordDTO);
    }

    @PostMapping("/check-expiry")
    public ApiResponse checkTokenExpiry(@RequestBody ForgotPasswordDto forgotPasswordDTO){
        return  forgotPasswordService.checkTokenExpiry(forgotPasswordDTO);
    }

    @PostMapping("/save-pass")
    public ApiResponse saveNewPassword(@RequestBody ForgotPasswordDto forgotPasswordDTO){
        return forgotPasswordService.saveNewPassword(forgotPasswordDTO);
    }
}
