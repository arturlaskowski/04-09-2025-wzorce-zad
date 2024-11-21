package wzorce.oop;

import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

}
