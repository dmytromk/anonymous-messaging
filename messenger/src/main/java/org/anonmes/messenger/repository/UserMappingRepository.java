package org.anonmes.messenger.repository;

import org.anonmes.messenger.model.UserMapping;
import org.anonmes.messenger.model.UserMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //Temp, remove if needed
public interface UserMappingRepository extends JpaRepository<UserMapping, UserMappingId> {
}
