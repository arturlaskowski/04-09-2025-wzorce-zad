package wzorce.observer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface HRManagementRepository extends JpaRepository<HRManagement, Long> {

    Optional<HRManagement> findByProjectId(Long projectId);
}
