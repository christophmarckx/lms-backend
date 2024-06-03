package com.switchfully.lmstrapeziumbackend.user.coach;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CoachService {
    private final UserRepository userRepository;

    public CoachService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoachDTO> getCoachesFollowingClass(UUID classId) {
//        return this.userRepository
//                .findAllByClassgroups(classId)
//                .stream().map(CoachMapper::toDTO)
//                .toList();
        return new ArrayList<CoachDTO>();

    }
}
