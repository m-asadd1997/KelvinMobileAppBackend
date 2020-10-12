package com.example.excelProj;

import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

//    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Autowired
    private UserDaoRepository repository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public void run(String... args) throws Exception {
//        repository.save(new User("admin","admin",bcryptEncoder.encode("admin"),true,"admin"));
//        String ip = InetAddress.getLocalHost().getHostAddress();
//        Constants.galleryImagePath = "https://"+ip+":8447/api/gallery/";

    }
}
