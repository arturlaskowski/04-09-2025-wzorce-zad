package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FinanceManagementProjectListener {

    private final FinanceService financeService;

    @EventListener
    public void handleEvent(ProjectInitializedEvent projectInitializedEvent) {
        financeService.allocateInitialBudget(projectInitializedEvent.projectId());
    }

    @EventListener
    public void handleEvent(ProjectSuspendedEvent projectSuspendedEvent) {
        financeService.freezeBudget(projectSuspendedEvent.projectId());
    }

    @EventListener
    public void handleEvent(ProjectCompletedEvent projectCompletedEvent) {
        financeService.finalizeProjectAccounts(projectCompletedEvent.projectId());
    }
}