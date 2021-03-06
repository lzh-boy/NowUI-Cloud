package com.nowui.cloud.eureka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ZhongYongQiang
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, ElasticsearchAutoConfiguration.class, RabbitAutoConfiguration.class, RedisAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableEurekaServer
public class ServerEurekaApplication {

    public static void main(String[] args){

//    	try {
//    		SpringApplication.run(ServerEurekaApplication.class, args);
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	}
        SpringApplication.run(ServerEurekaApplication.class, args);
    }

}