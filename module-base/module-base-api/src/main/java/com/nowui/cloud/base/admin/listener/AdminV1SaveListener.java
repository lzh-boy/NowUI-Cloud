package com.nowui.cloud.base.admin.listener;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.base.admin.repository.AdminRepository;
import com.nowui.cloud.base.admin.router.AdminRouter;
import com.nowui.cloud.base.admin.view.AdminView;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.rabbit.RabbitListener;

/**
 * 管理员新增消息队列
 *
 * @author marcus
 *
 * 2018-02-03
 */
@Configuration
public class AdminV1SaveListener {

    private final String queueName = "admin_v1_save";

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    Queue AdminV1SaveQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding AdminV1SaveQueueBindingExchange(Queue AdminV1SaveQueue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(AdminV1SaveQueue).to(exchange).with(AdminRouter.ADMIN_V1_SAVE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public MessageListenerContainer AdminV1SaveMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(AdminV1SaveMessageListener());
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setPrefetchCount(Constant.PREFETCH_COUNT);
        return simpleMessageListenerContainer;
    }

    @Bean
    public RabbitListener AdminV1SaveMessageListener() {
        return new RabbitListener() {

            @Override
            public void receive(String message) {
                AdminView adminView = JSON.parseObject(message, AdminView.class);

                adminRepository.save(adminView);
            }

        };
    }

}
