package org.energyapp.repository;

import org.energyapp.model.TestUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestUserRepo extends JpaRepository<TestUser, Long> {
}
