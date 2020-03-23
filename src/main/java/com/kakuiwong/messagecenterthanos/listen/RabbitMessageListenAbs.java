package com.kakuiwong.messagecenterthanos.listen;

import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;

/**
 * @author: gaoyang
 * @Description:
 */
public abstract class RabbitMessageListenAbs {

    public abstract boolean checkIsResume(ZMessageOne zMessageOne);

    public abstract void handleMsg(ZMessageOne zMessageOne);
}
