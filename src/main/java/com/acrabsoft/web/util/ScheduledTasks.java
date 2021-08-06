package com.acrabsoft.web.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@EnableAsync
@EnableScheduling
public class ScheduledTasks {
    Logger logger = LoggerFactory.getLogger( ScheduledTasks.class);

    @Value("${spring.profiles.active}")


    /**
     * @description  每五分钟执行的定时任务
     * @date  20/07/16 10:26
     * @author  wanghb
     * @edit
     */
    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public void refreshParams(){
        logger.info("===================1秒");
    }


    /**
     * @description  每五分钟执行的定时任务
     * @date  20/07/16 10:26
     * @author  wanghb
     * @edit
     */
    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public synchronized void refreshParams1() {
        try {
            Thread.sleep( 5000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("===================5秒");
    }


}
