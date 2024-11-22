package wzorce.observer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class FinanceService {

    private final FinanceManagementRepository financeManagementRepository;

    public void allocateInitialBudget(Long projectId) {
        var financeManagement = new FinanceManagement(projectId);
        financeManagementRepository.save(financeManagement);
        log.info("Przydzielono budżet dla projektu: " + projectId);
    }

    @Transactional
    public void freezeBudget(Long projectId) {
        var financeManagement = financeManagementRepository.findByProjectId(projectId).orElseThrow();
        financeManagement.freezeBudget();
        log.info("Zamrożono budżet projektu " + projectId);
    }

    @Transactional
    public void finalizeProjectAccounts(Long projectId) {
        var financeManagement = financeManagementRepository.findByProjectId(projectId).orElseThrow();
        financeManagement.finalizeProjectAccounts();
        log.info("Rozliczenie finansowe projektu " + projectId + " zostało przeprowadzone");

    }
}
