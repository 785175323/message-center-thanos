package com.kakuiwong.messagecenterthanos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kakuiwong.messagecenterthanos.entity.ZMessageLog;
import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gaoyang
 * @since 2020-03-23
 */
public interface IZMessageLogService extends IService<ZMessageLog> {

    ZMessageLog getByMessageAndQueueExchange(ZMessageOne zMessageOne, String queue, String exchange);

    boolean updateByMessage(ZMessageOne zMessageOne, String exchange, String queue);
}
