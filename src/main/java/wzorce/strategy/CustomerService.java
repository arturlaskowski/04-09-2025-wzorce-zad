package wzorce.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerNotificationService notificationScheduler;

    @Transactional
    public Long createCustomer(CustomerCreateDto dto) {
        var customer = new Customer(dto.firstName(), dto.lastName(),
                new CustomerContact(dto.email(), dto.phoneNumber(), dto.deviceToken(), dto.communicationPreference()));

        customerRepository.save(customer);
        notificationScheduler.scheduleNotification(customer);
        return customer.getId();
    }
}
