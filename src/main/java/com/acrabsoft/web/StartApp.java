package com.acrabsoft.web;

import com.acrabsoft.web.util.ScheduledTasks;
import com.acrabsoft.web.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJpaAuditing
public class StartApp
{
    public static void main( String[] args )
    {

    	SpringApplication.run(App.class, args);
        SpringContextUtil.getBean( ScheduledTasks.class ).refreshParams1();
        SpringContextUtil.getBean( ScheduledTasks.class ).refreshParams();
        System.out.println("===============================>项目启动成功<===============================");
    }
}
