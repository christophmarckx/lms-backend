package com.switchfully.lmstrapeziumbackend.user.coach;

import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;

public class CoachMapper {
    public static CoachDTO toDTO(User coach) {
        return new CoachDTO(coach.getId(), coach.getEmail(), coach.getDisplayName());
    }
}
