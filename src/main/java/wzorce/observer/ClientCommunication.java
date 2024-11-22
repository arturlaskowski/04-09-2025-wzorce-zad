package wzorce.observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ClientCommunication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long projectId;
    private boolean isClientNotifiedOfProjectStart;
    private boolean isClientNotifiedOfProjectEnd;

    public ClientCommunication(Long projectId) {
        this.projectId = projectId;
    }

    void notifyClientOfProjectStart() {
        isClientNotifiedOfProjectStart = true;
    }

    void notifyClientOfProjectEnd() {
        isClientNotifiedOfProjectEnd = true;
    }
}
