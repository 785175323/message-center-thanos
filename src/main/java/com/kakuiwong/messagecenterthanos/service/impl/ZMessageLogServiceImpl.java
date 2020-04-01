package com.kakuiwong.messagecenterthanos.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kakuiwong.messagecenterthanos.entity.ZMessageLog;
import com.kakuiwong.messagecenterthanos.entity.ZMessageOne;
import com.kakuiwong.messagecenterthanos.entity.enums.MessageLogEnum;
import com.kakuiwong.messagecenterthanos.mapper.ZMessageLogMapper;
import com.kakuiwong.messagecenterthanos.service.IZMessageLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gaoyang
 * @since 2020-03-23
 */
@Service
public class ZMessageLogServiceImpl extends ServiceImpl<ZMessageLogMapper, ZMessageLog> implements IZMessageLogService {

    @Override
    public ZMessageLog getByMessageAndQueueExchange(ZMessageOne zMessageOne, String queue, String exchange) {
        return this.baseMapper.selectOne(new QueryWrapper<ZMessageLog>().
                eq("msgExchange", exchange).
                eq("queue", queue).
                eq("msgId", zMessageOne.getId()));
    }

    @Override
    public boolean updateByMessage(ZMessageOne zMessageOne, String exchange, String queue) {
        return this.update(new UpdateWrapper<ZMessageLog>().
                eq("msgExchange", exchange).
                eq("queue", queue).
                eq("msgId", zMessageOne.getId()).
                set("result", MessageLogEnum.SUCCESS.toString()));

    }
}
