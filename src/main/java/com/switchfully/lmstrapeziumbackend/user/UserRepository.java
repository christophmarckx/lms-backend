package com.switchfully.lmstrapeziumbackend.user;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByClassgroupsAndRole(Classgroup classgroup, UserRole role);
}
