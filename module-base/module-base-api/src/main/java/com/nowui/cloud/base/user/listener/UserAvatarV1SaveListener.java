package com.nowui.cloud.base.user.listener;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.rabbit.RabbitListener;
import com.nowui.cloud.base.user.service.UserAvatarService;
import com.nowui.cloud.base.user.router.UserAvatarRouter;
import com.nowui.cloud.base.user.view.UserAvatarView;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户头像新增消息队列
 *
 * @author marcus
 *
 * 2018-02-04
 */
@Configuration
public class UserAvatarV1SaveListener {

    private final String queueName = "user_avatar_v1_save";

    @Autowired
    private UserAvatarService userAvatarService;

    @Bean
    Queue UserAvatarV1SaveQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding UserAvatarV1SaveQueueBindingExchange(Queue UserAvatarV1SaveQueue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(UserAvatarV1SaveQueue).to(exchange).with(UserAvatarRouter.USER_AVATAR_V1_SAVE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public MessageListenerContainer UserAvatarV1SaveMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(UserAvatarV1SaveMessageListener());
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setPrefetchCount(Constant.PREFETCH_COUNT);
        return simpleMessageListenerContainer;
    }

    @Bean
    public RabbitListener UserAvatarV1SaveMessageListener() {
        return new RabbitListener() {

            @Override
            public void receive(String message) {
                UserAvatarView userAvatarView = JSON.parseObject(message, UserAvatarView.class);

                userAvatarService.save(userAvatarView);
            }

        };
    }

}
