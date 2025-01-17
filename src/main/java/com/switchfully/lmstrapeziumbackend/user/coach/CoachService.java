package com.switchfully.lmstrapeziumbackend.user.coach;

import com.switchfully.lmstrapeziumbackend.classgroup.Classgroup;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import com.switchfully.lmstrapeziumbackend.user.dto.CoachDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoachService {
    private final UserRepository userRepository;

    public CoachService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoachDTO> getCoachesFollowingClass(Classgroup classgroup) {
        return this.userRepository
                .findAllByClassgroupsAndRole(classgroup, UserRole.COACH)
                .stream().map(CoachMapper::toDTO)
                .toList();
    }

    public List<CoachDTO> getAllCoaches() {
        return this.userRepository
                .findAllByRole(UserRole.COACH)
                .stream()
                .map(CoachMapper::toDTO)
                .collect(Collectors.toList());
    }
}
