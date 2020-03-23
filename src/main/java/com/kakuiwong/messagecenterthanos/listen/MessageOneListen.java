package com.kakuiwong.messagecenterthanos.listen;

import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DuplicateKeyException;

/**
 * @author: gaoyang
 * @Description:
 */
@RabbitListener()
public class MessageOneListen extends RabbitMessageListenAbs {

    //Todo

    //幂等性,验证log
    @Override
    public boolean checkIsResume(ZMessageOne zMessageOne) {
        try {
            //添加log

        } catch (DuplicateKeyException e) {
            //验证log是否success

            return false;
        }
        return true;
    }

    //执行任务
    @Override
    public void handleMsg(ZMessageOne zMessageOne) {
        //如判断任务完成状态重新生成db任务并更新id,则下面log commit,否则根据事务直接提交log

        //commit log
    }
}
