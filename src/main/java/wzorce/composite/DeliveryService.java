package wzorce.composite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public void prepareDelivery(String transactionId, PackageComponent packageComponent, String address) {
        var packagePrice = packageComponent.calculatePrice();
        var packageWeight = packageComponent.calculateWeight();
        var delivery = new Delivery(transactionId, packagePrice, packageWeight, address);
        deliveryRepository.save(delivery);
    }
}

