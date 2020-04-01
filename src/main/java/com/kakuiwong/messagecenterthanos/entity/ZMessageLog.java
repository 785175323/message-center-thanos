package com.kakuiwong.messagecenterthanos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kakuiwong.messagecenterthanos.entity.enums.MessageLogEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author gaoyang
 * @since 2020-03-23
 * msgId + msgExchange + queue 组合唯一键
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZMessageLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private String msgExchange;

    private String msgType;

    private String msgId;

    private String queue;

    private String result;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Version
    private Integer version;


    public static ZMessageLog createByMessageOne(ZMessageOne zMessageOne) {
        ZMessageLog zMessageLog = new ZMessageLog();
        BeanUtils.copyProperties(zMessageOne, zMessageLog);
        LocalDateTime now = LocalDateTime.now();
        zMessageLog.setId(IdWorker.getId());
        zMessageLog.setCreateTime(now);
        zMessageLog.setUpdateTime(now);
        zMessageLog.setResult(MessageLogEnum.PROCESSING.toString());
        return zMessageLog;
    }
}
