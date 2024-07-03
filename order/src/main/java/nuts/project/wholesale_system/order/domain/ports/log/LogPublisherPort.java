package nuts.project.wholesale_system.order.domain.ports.log;

public interface LogPublisherPort {
    void publishing(Object message);
}
