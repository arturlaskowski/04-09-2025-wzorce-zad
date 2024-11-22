package wzorce.observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class FinanceManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private boolean isFreezeBudget;
    private boolean isFinalizeProjectAccounts;

    public FinanceManagement(Long projectId) {
        this.projectId = projectId;
    }

    void freezeBudget() {
        isFreezeBudget = true;
    }

    void finalizeProjectAccounts() {
        isFinalizeProjectAccounts = true;
    }
}
