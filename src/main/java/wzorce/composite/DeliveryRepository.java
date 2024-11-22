package wzorce.composite;

import org.springframework.data.jpa.repository.JpaRepository;

interface DeliveryRepository extends JpaRepository<Delivery, String> {
}
