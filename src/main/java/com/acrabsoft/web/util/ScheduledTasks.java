package com.acrabsoft.web.util;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Component
@EnableAsync
@EnableScheduling
public class ScheduledTasks {
    Logger logger = LoggerFactory.getLogger( ScheduledTasks.class);

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * @description  每五分钟执行的定时任务
     * @date  20/07/16 10:26
     * @author  wanghb
     * @edit
     */
    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    //@Transactional(rollbackOn = Exception.class)
    public void refreshParams(){
        Object o = null;
        if (o == null) {
            System.out.println(o.toString());
        }
        logger.info("===================1秒");
    }


    /**
     * @description  每五分钟执行的定时任务
     * @date  20/07/16 10:26
     * @author  wanghb
     * @edit
     */
    private static Object lock = new Object();

    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    //@Transactional(rollbackOn = Exception.class)
    public void refreshParams1() {
        synchronized (lock){
            try {
                Thread.sleep( 5000 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("===================5秒");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add( "HZ224002100000202108270000000041" );
        list.add( "HZ213299200000202108270000000041" );
        list.add( "HZ900417200000202108270000000041" );
        list.add( "HZ900294600000202108270000000041" );
        list.add( "HZ210003400000202108270000000041" );
        list.add( "HZ214001900000202108270000000041" );
        list.add( "HZ222000300000202108270000000041" );
        list.add( "HZ226100100000202108270000000041" );
        list.add( "HZ900252400000202108270000000041" );
        list.add( "HZ224002100000202108270000000350" );
        list.add( "HZ213299200000202108270000000350" );
        list.add( "HZ900417200000202108270000000350" );
        list.add( "HZ900294600000202108270000000350" );
        list.add( "HZ210003400000202108270000000350" );
        list.add( "HZ214001900000202108270000000350" );
        list.add( "HZ222000300000202108270000000350" );
        list.add( "HZ226100100000202108270000000350" );
        list.add( "HZ900252400000202108270000000350" );
        list.add( "HZ900252400000202108190000000330" );
        list.add( "HZ226100100000202108190000000330" );
        list.add( "HZ900021400000202108190000000330" );
        list.add( "HZ214001900000202108190000000330" );
        list.add( "HZ900417200000202108190000000330" );
        list.add( "HZ213299200000202108190000000330" );
        list.add( "HZ222000300000202108190000000330" );
        list.add( "HZ210003400000202108190000000330" );
        list.add( "HZ900294600000202108190000000330" );
        list.add( "HZ224002100000202108190000000330" );
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("serial",list.get( i ));
            try {
                Map<String, Object> post = HttpUtil.post( "http://221.226.3.55:40089/hostelBasis/updateStatus", temp );
                Thread.sleep( 100 );
                System.out.println(list.get( i )+"=========>"+ JSON .toJSONString( post ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
