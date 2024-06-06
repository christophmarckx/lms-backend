package com.switchfully.lmstrapeziumbackend.progress;

import com.switchfully.lmstrapeziumbackend.codelab.Codelab;
import com.switchfully.lmstrapeziumbackend.user.User;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public CodelabProgress getCodelabProgress(Codelab codelab, User user) {
        return progressRepository.findByCodelabAndUser(codelab, user)
                .orElseGet(() -> new Progress(null, null, CodelabProgress.NOT_STARTED))
                .getStatus();
    }
}
