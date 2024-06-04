package com.switchfully.lmstrapeziumbackend.classgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassgroupRepository extends JpaRepository<Classgroup, UUID> {
}
