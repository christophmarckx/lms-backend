package com.switchfully.lmstrapeziumbackend.codelab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodelabRepository extends JpaRepository<Codelab, UUID> {

}
