package wzorce.observer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
class HRService {

    private final HRManagementRepository hrManagementRepository;

    @Transactional
    public void assignProjectTeam(Long projectId) {
        var hrManagement = new HRManagement(projectId);
        hrManagementRepository.save(hrManagement);
        log.info("Przydzielono zespół projektowy dla projektu: " + projectId);
    }

    @Transactional
    public void reallocateResources(Long projectId) {
        var hrManagement = hrManagementRepository.findByProjectId(projectId).orElseThrow();
        hrManagement.reallocateResources();
        log.info("Odpięto zespół od prjketu " + projectId);
    }
}
