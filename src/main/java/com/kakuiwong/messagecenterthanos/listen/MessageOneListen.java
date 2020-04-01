package com.kakuiwong.messagecenterthanos.listen;

import com.kakuiwong.messagecenterthanos.entity.ZMessageLog;
import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;
import com.kakuiwong.messagecenterthanos.service.IZMessageLogService;
import com.kakuiwong.messagecenterthanos.service.IZMessageOneService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author: gaoyang
 * @Description: 消息处理
 */
@Component
@RabbitListener(queues = MessageOneListen.QUEUE)
public class MessageOneListen extends RabbitMessageListenAbs {

    public final static String QUEUE = "one";
    public final static String EXCHANGE = "one";
    @Autowired
    private IZMessageLogService izMessageLogService;

    /**
     * 接收任务
     * @param zMessageOne
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitHandler
    @Override
    public void onListen(ZMessageOne zMessageOne, Channel channel, Message message) throws IOException {
        this.onListenHandle(zMessageOne, channel, message);
    }

    /**
     * 实际业务处理代码
     * @param zMessageOne
     * @return
     */
    @Transactional
    @Override
    public boolean handleMessage(ZMessageOne zMessageOne) {
        //处理消息
        return false;
    }

    /**
     * 设置消息日志为成功
     * @param zMessageOne
     * @return
     */
    @Transactional
    @Override
    public boolean commitLog(ZMessageOne zMessageOne) {
        return izMessageLogService.updateByMessage(zMessageOne, EXCHANGE, QUEUE);
    }

    /**
     * 根据 消息id,queue,exchange 获取日志
     * @param zMessageOne
     * @return
     */
    @Override
    public ZMessageLog getLogByMessageIdExchangeQueue(ZMessageOne zMessageOne) {
        return izMessageLogService.getByMessageAndQueueExchange(zMessageOne, QUEUE, EXCHANGE);
    }

    /**
     * 新增日志
     * @param zMessageOne
     * @return
     */
    @Override
    public boolean insertLog(ZMessageOne zMessageOne) {
        ZMessageLog byMessageOne = ZMessageLog.createByMessageOne(zMessageOne);
        byMessageOne.setQueue(QUEUE);
        return izMessageLogService.save(byMessageOne);
    }
}
