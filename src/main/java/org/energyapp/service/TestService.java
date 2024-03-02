package org.energyapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.energyapp.model.TestUser;
import org.energyapp.repository.TestUserRepo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestUserRepo testUserRepo;


    public void registerUser(TestUser testUser) {

        // validations
        testUserRepo.save(testUser);
        log.info("User: " + testUser.getUsername() + " has been created");
    }
}
