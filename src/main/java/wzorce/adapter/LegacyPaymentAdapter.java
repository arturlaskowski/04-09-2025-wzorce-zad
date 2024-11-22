package wzorce.adapter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "payment.system", havingValue = "legacy")
class LegacyPaymentAdapter {
}