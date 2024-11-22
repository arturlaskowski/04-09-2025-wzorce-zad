package wzorce.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void prepareDelivery(String transactionId, List<Box> boxes, String address) {
        BigDecimal boxesCost = boxes.stream()
                .map(Box::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int boxesWeight = boxes.stream()
                .mapToInt(Box::calculateWeight)
                .sum();

        var delivery = new Delivery(transactionId, boxesCost, boxesWeight, address);
        deliveryRepository.save(delivery);
    }

}

