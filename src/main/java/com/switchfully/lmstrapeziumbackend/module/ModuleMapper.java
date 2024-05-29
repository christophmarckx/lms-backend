package com.switchfully.lmstrapeziumbackend.module;

import com.switchfully.lmstrapeziumbackend.course.CourseMapper;
import com.switchfully.lmstrapeziumbackend.module.dto.CreateModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleDTO;
import com.switchfully.lmstrapeziumbackend.module.dto.ModuleWithCoursesDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ModuleMapper {
    public static ModuleDTO toDTO(Module module) {
        ModuleDTO parentModule = null;
        if (module.getParentModule() != null){
            parentModule = ModuleMapper.toDTO(module.getParentModule());
        }
        return new ModuleDTO(
                module.getId(),
                module.getName(),
                parentModule);
    }

    public static Module toModule(CreateModuleDTO createModuleDTO, Module parentModule) {

        return new Module(
                createModuleDTO.name(),
                parentModule
        );
    }

    public static ModuleWithCoursesDTO toModuleWithCoursesDTO(Module module) {
        return new ModuleWithCoursesDTO(module.getId(), module.getName(), CourseMapper.toDTO(module.getCourses()));
    }
}
