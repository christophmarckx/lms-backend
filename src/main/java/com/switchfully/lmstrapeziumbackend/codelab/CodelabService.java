package com.switchfully.lmstrapeziumbackend.codelab;

import com.switchfully.lmstrapeziumbackend.codelab.dto.CodelabDTO;
import com.switchfully.lmstrapeziumbackend.codelab.dto.CreateCodelabDTO;
import com.switchfully.lmstrapeziumbackend.exception.AccessForbiddenException;
import com.switchfully.lmstrapeziumbackend.exception.CodelabNotFoundException;
import com.switchfully.lmstrapeziumbackend.exception.UserNotFoundException;
import com.switchfully.lmstrapeziumbackend.module.Module;
import com.switchfully.lmstrapeziumbackend.module.ModuleService;
import com.switchfully.lmstrapeziumbackend.progress.CodelabProgress;
import com.switchfully.lmstrapeziumbackend.progress.Progress;
import com.switchfully.lmstrapeziumbackend.progress.ProgressRepository;
import com.switchfully.lmstrapeziumbackend.security.AuthenticationService;
import com.switchfully.lmstrapeziumbackend.user.User;
import com.switchfully.lmstrapeziumbackend.user.UserRepository;
import com.switchfully.lmstrapeziumbackend.user.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CodelabService {
    private final CodelabRepository codelabRepository;
    private final ModuleService moduleService;
    private final ProgressRepository progressRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public CodelabService(CodelabRepository codelabRepository, ModuleService moduleService,
                          ProgressRepository progressRepository, AuthenticationService authenticationService,
                          UserRepository userRepository) {
        this.codelabRepository = codelabRepository;
        this.moduleService = moduleService;
        this.progressRepository = progressRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    public CodelabDTO createCodelab(CreateCodelabDTO codelabDTO) {
        Module codelabParentModule = moduleService.getModuleById(codelabDTO.parentModuleId());
        Codelab codelabToSave = CodelabMapper.toCodelab(codelabDTO, codelabParentModule);
        Codelab savedCodelab = codelabRepository.save(codelabToSave);
        return CodelabMapper.toDTO(savedCodelab);
    }

    public List<Codelab> getCodelabsByModuleId(UUID moduleId) {
        return codelabRepository.findCodelabsByModuleId(moduleId);
    }
  
    public List<CodelabDTO> getAllCodelabs() {
        return CodelabMapper.toDTO(codelabRepository.findAll());
    }

    public CodelabDTO getById(UUID id) {
        return CodelabMapper.toDTO(codelabRepository.findById(id).orElseThrow(() -> new CodelabNotFoundException(id)));
    }

    public CodelabDTO updateCodelabProgress(UUID codelabId, CodelabProgress codelabProgress, Authentication authentication) {
        if (!authenticationService.getAuthenticatedUserRole(authentication).equals(UserRole.STUDENT)) {
            throw new AccessForbiddenException();
        }

        UUID studentId = authenticationService.getAuthenticatedUserId(authentication)
                .orElseThrow(UserNotFoundException::new);

        User student = userRepository.findById(studentId)
                .orElseThrow(UserNotFoundException::new);

        Codelab codelab = codelabRepository.findById(codelabId)
                .orElseThrow(() -> new CodelabNotFoundException(codelabId));

        Progress progress = progressRepository.findByCodelabAndUser(codelab, student)
                .orElseGet(() -> new Progress(codelab, student, null));

        progress.updateStatus(codelabProgress);

        if (codelabProgress.equals(CodelabProgress.NOT_STARTED))
            progressRepository.delete(progress);
        else
            progressRepository.save(progress);

        return CodelabMapper.toDTO(codelab, codelabProgress);
    }
}
