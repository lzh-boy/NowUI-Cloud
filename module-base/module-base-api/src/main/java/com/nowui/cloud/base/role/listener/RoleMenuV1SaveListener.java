package com.nowui.cloud.base.role.listener;

import com.alibaba.fastjson.JSON;
import com.nowui.cloud.constant.Constant;
import com.nowui.cloud.rabbit.RabbitListener;
import com.nowui.cloud.base.role.service.RoleMenuService;
import com.nowui.cloud.base.role.router.RoleMenuRouter;
import com.nowui.cloud.base.role.view.RoleMenuView;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 角色菜单新增消息队列
 *
 * @author marcus
 *
 * 2018-02-04
 */
//@Configuration
public class RoleMenuV1SaveListener {

    private final String queueName = "role_menu_v1_save";

    @Autowired
    private RoleMenuService roleMenuService;

    @Bean
    Queue RoleMenuV1SaveQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    Binding RoleMenuV1SaveQueueBindingExchange(Queue RoleMenuV1SaveQueue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(RoleMenuV1SaveQueue).to(exchange).with(RoleMenuRouter.ROLE_MENU_V1_SAVE);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public MessageListenerContainer RoleMenuV1SaveMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(queueName);
        simpleMessageListenerContainer.setMessageListener(RoleMenuV1SaveMessageListener());
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setPrefetchCount(Constant.PREFETCH_COUNT);
        return simpleMessageListenerContainer;
    }

    @Bean
    public RabbitListener RoleMenuV1SaveMessageListener() {
        return new RabbitListener() {

            @Override
            public void receive(String message) {
                RoleMenuView roleMenuView = JSON.parseObject(message, RoleMenuView.class);

                roleMenuService.save(roleMenuView);
            }

        };
    }

}