package com.kakuiwong.messagecenterthanos.listen;

import com.kakuiwong.messagecenterthanos.entity.ZMessageLog;
import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;
import com.kakuiwong.messagecenterthanos.entity.enums.MessageLogEnum;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author: gaoyang
 * @Description:
 */
public abstract class RabbitMessageListenAbs {

    public abstract void onListen(ZMessageOne zMessageOne, Channel channel, Message message) throws IOException;

    public abstract boolean handleMessage(ZMessageOne zMessageOne);

    public abstract ZMessageLog getLogByMessageIdExchangeQueue(ZMessageOne zMessageOne);

    public abstract boolean insertLog(ZMessageOne zMessageOne);

    public abstract boolean commitLog(ZMessageOne zMessageOne);

    /**
     * 幂等性验证
     *
     * @param zMessageOne
     * @return
     */
    protected boolean checkIsCommit(ZMessageOne zMessageOne) {
        try {
            insertLog(zMessageOne);
        } catch (DuplicateKeyException e) {
            return getLogByMessageIdExchangeQueue(zMessageOne).getResult().equals(MessageLogEnum.SUCCESS.toString());
        }
        return false;
    }

    /**
     * 处理接收到的消息
     *
     * @param zMessageOne
     * @param channel
     * @param message
     * @throws IOException
     */
    @Transactional
    protected void onListenHandle(ZMessageOne zMessageOne, Channel channel, Message message) throws IOException {
        if (checkIsCommit(zMessageOne)) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
        if (handleMessage(zMessageOne)) {
            commitLog(zMessageOne);
        }
    }
}
