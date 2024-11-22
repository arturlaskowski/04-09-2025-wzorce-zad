package wzorce.observer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private ProjectStatus status;

    public Project(String name) {
        this.name = name;
        this.status = ProjectStatus.PENDING;
    }

    public void start() {
        if (status != ProjectStatus.PENDING && status != ProjectStatus.SUSPENDED) {
            throw new IllegalStateException("Project can only be started if it is in PENDING or SUSPENDED status.");
        }
        status = ProjectStatus.IN_PROGRESS;
    }

    public void suspend() {
        if (status != ProjectStatus.IN_PROGRESS) {
            throw new IllegalStateException("Project can only be suspended if it is in IN_PROGRESS status.");
        }
        status = ProjectStatus.SUSPENDED;
    }

    public void complete() {
        if (status == ProjectStatus.COMPLETED) {
            throw new IllegalStateException("Project cannot be completed again as it is already in COMPLETED status.");
        }
        status = ProjectStatus.COMPLETED;
    }
}
