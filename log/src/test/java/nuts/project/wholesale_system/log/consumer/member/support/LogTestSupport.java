package nuts.project.wholesale_system.log.consumer.member.support;

import jakarta.transaction.Transactional;
import nuts.project.wholesale_system.log.repository.member.MemberLogRepository;
import nuts.project.wholesale_system.log.repository.order.OrderLogRepository;
import nuts.project.wholesale_system.log.repository.stock.StockLogRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogTestSupport{

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @Autowired
    protected MemberLogRepository memberLogRepository;

    @Autowired
    protected OrderLogRepository orderLogRepository;

    @Autowired
    protected StockLogRepository stockLogRepository;
}