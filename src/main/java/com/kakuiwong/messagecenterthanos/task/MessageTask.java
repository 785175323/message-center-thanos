package com.kakuiwong.messagecenterthanos.task;

import com.kakuiwong.lockthanos.annotation.LockThanos;
import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;
import com.kakuiwong.messagecenterthanos.listen.MessageOneListen;
import com.kakuiwong.messagecenterthanos.service.IZMessageOneService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: gaoyang
 * @Description: 定时任务
 */
@Component
public class MessageTask {

    public final static String LOCKNAME = "task";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IZMessageOneService izMessageOneService;

    /**
     * 查询任务执行,使用自主开源分布式锁
     */
    @LockThanos(lockName = LOCKNAME)
    @Scheduled(fixedDelay = 10000)
    public void run() {
        List<ZMessageOne> list = izMessageOneService.list();
        list.stream().forEach(this::sendMessageOne);
    }

    /**
     * 发送任务到队列
     *
     * @param zMessageOne
     */
    @Transactional
    public void sendMessageOne(ZMessageOne zMessageOne) {
        //删除db任务
        izMessageOneService.removeById(zMessageOne);
        //发送mq
        rabbitTemplate.convertAndSend(MessageOneListen.EXCHANGE, MessageOneListen.QUEUE, zMessageOne);
    }
}
