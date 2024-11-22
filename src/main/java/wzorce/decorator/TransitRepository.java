package wzorce.decorator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface TransitRepository extends JpaRepository<Transit, UUID> {
}
