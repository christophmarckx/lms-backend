package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID>{
    List<Module> findModulesByParentModuleNull();
}
