package wzorce.oop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE payment_card SET balance = ?2, version = version +1 WHERE id = ?1 AND version=?3", nativeQuery = true)
    int updateBalanceWithOptimisticLock(Long cardId, BigDecimal withdrawAmount, int currentVersion);
}
