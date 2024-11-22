package wzorce.decorator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class TransitService {

    private final TransitRepository transitRepository;
    private final TariffFactory tariffFactory;

    public UUID startTransit(UUID customerId, LocalDateTime requestTime) {
        var transit = new Transit(customerId, requestTime);
        transitRepository.save(transit);
        return transit.getId();
    }

    @Transactional
    public void finishTransit(UUID transitId, int distance) {
        var transit = transitRepository.findById(transitId)
                .orElseThrow(() -> new TransitNotExistsException(transitId));

        var tariff = tariffFactory.createTariff(transit.getRequestTime());
        var totalCost = tariff.calculateCost(distance);

        transit.finish(distance, tariff.getName(), totalCost);
    }
}
