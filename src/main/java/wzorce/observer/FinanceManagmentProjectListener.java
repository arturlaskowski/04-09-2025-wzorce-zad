package wzorce.observer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FinanceManagmentProjectListener implements ProjectListener {

    private final FinanceService financeService;

    @Override
    public void update(ProjectDto projectDto) {
        switch (projectDto.status()) {
            case PENDING -> financeService.allocateInitialBudget(projectDto.id());
            case SUSPENDED -> financeService.freezeBudget(projectDto.id());
            case COMPLETED -> financeService.finalizeProjectAccounts(projectDto.id());
        }
    }
}