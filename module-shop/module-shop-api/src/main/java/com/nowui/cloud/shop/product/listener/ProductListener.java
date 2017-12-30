package com.nowui.cloud.shop.product.listener;

import com.nowui.cloud.constant.Constant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ZhongYongQiang
 */
@Component
@RabbitListener(queues = Constant.QUEUE)
public class ProductListener {

    @RabbitHandler
    public void process(String message) {
        System.out.println(String.format("receive message: %s", message));
    }

}
