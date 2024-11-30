package wzorce.cqrs.common;

public interface CommandHandlerExecutor {

    void execute(Command command);
}
