package org.anonmes.messenger.repository;

import org.anonmes.messenger.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //Temp, remove if needed
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
}
