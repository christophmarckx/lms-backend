package com.switchfully.lmstrapeziumbackend.classgroup.dto;

import com.switchfully.lmstrapeziumbackend.course.dto.CourseDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import com.switchfully.lmstrapeziumbackend.user.dto.StudentDTO;

import java.util.List;
import java.util.UUID;

public record ClassgroupWithMembersDTO(
        UUID id,
        String name,
        CourseDTO course,
        List<StudentDTO> students,
        List<CoachDTO> coaches
) {
}
