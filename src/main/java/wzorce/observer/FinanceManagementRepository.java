package wzorce.observer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface FinanceManagementRepository extends JpaRepository<FinanceManagement, Long> {

    Optional<FinanceManagement> findByProjectId(Long projectId);
}
