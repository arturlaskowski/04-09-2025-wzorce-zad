package wzorce.observer;

record ProjectChangedEvent(Long id, String name, ProjectStatus status) {
}