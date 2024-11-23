package wzorce.strategy;


import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static wzorce.strategy.NotificationChannel.*;

@SpringBootTest
class SendCustomerNotificationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerNotificationScheduler customerNotificationScheduler;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
        notificationRepository.deleteAll();
    }

    @Test
    void sendEmailToCustomerTest() {
        //given
        var customerCommunicationPreference = EMAIL;
        var customerCreateDto = createCustomerDtoWithCommunicationPreference(customerCommunicationPreference);
        var customerId = customerService.createCustomer(customerCreateDto);

        //when
        customerNotificationScheduler.sendPendingNotifications();

        //then
        assertCustomerNotification(customerId, customerCommunicationPreference);
    }

    @Test
    void sendPushToCustomerTest() {
        //given
        var customerCommunicationPreference = PUSH_NOTIFICATION;
        var customerCreateDto = createCustomerDtoWithCommunicationPreference(customerCommunicationPreference);
        var customerId = customerService.createCustomer(customerCreateDto);

        //when
        customerNotificationScheduler.sendPendingNotifications();

        //then
        assertCustomerNotification(customerId, customerCommunicationPreference);
    }


    @Test
    void sendSmsToCustomerTest() {
        //given
        var customerCommunicationPreference = SMS;
        var customerCreateDto = createCustomerDtoWithCommunicationPreference(customerCommunicationPreference);
        var customerId = customerService.createCustomer(customerCreateDto);

        //when
        customerNotificationScheduler.sendPendingNotifications();

        //then
        assertCustomerNotification(customerId, customerCommunicationPreference);
    }

    @Test
    void customerNotificationServiceShouldBeDecoupledFromSpecificSenders() {
        var importedClasses = new ClassFileImporter().importPackages("wzorce.strategy");

        var rule = noClasses()
                .that().areAssignableTo(CustomerNotificationService.class)
                .should().dependOnClassesThat().areAssignableTo(EmailNotificationSender.class)
                .orShould().dependOnClassesThat().areAssignableTo(PushNotificationSender.class)
                .orShould().dependOnClassesThat().areAssignableTo(SmsNotificationSender.class)
                .because("Zastosowanie wzorca Strategia pozwoli uniknąć bezpośredniej zależności między obiektem" +
                        " CustomerNotificationService a konkretnymi senderami.");


        rule.check(importedClasses);
    }

    private void assertCustomerNotification(Long customerId, NotificationChannel customerCommunicationPreference) {
        var lastCustomerNotification = notificationRepository.findFirstByCustomerIdOrderBySendAtDesc(customerId).orElseThrow();
        assertThat(lastCustomerNotification)
                .hasFieldOrPropertyWithValue("customerId", customerId)
                .hasFieldOrPropertyWithValue("messageTemplate", CustomerNotificationTemplate.WELCOME_MESSAGE.name())
                .hasFieldOrPropertyWithValue("channel", customerCommunicationPreference)
                .hasFieldOrPropertyWithValue("status", NotificationStatus.SENT);
    }

    private CustomerCreateDto createCustomerDtoWithCommunicationPreference(NotificationChannel communicationPreference) {
        return CustomerCreateDto.builder()
                .firstName("Waldemar")
                .lastName("Kiepski")
                .email("waldek@gmail.com")
                .phoneNumber("222111222")
                .deviceToken(UUID.randomUUID().toString())
                .communicationPreference(communicationPreference)
                .build();
    }
}