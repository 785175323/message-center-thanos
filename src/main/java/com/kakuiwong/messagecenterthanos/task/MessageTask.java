package com.kakuiwong.messagecenterthanos.task;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kakuiwong.messagecenterthanos.util.RedisLockUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: gaoyang
 * @Description:
 */
@Component
public class MessageTask {

    @Autowired
    private RedisLockUtil redisLockUtil;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final RedisLockUtil.LockParam param = new RedisLockUtil.LockParam("Message.one");

    // Todo
    @Scheduled(fixedDelay = 10000)
    public void run() {
        try {
            redisLockUtil.tryLock(param.setValue(IdWorker.getIdStr()));

            //从db取任务执行发送mq

        } finally {
            redisLockUtil.releaseLock(param);
        }
    }

    // Todo
    @Transactional
    public void sendMessageOne() {
        //删除db任务

        //发送mq
    }
}
