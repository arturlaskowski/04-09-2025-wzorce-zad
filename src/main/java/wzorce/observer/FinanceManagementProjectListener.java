package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FinanceManagementProjectListener {

    private final FinanceService financeService;

    @EventListener
    public void handleEvent(ProjectChangedEvent projectChangedEvent) {
        switch (projectChangedEvent.status()) {
            case PENDING -> financeService.allocateInitialBudget(projectChangedEvent.id());
            case SUSPENDED -> financeService.freezeBudget(projectChangedEvent.id());
            case COMPLETED -> financeService.finalizeProjectAccounts(projectChangedEvent.id());
        }
    }
}