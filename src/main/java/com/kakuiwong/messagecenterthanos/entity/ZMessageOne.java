package com.kakuiwong.messagecenterthanos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author gaoyang
 * @since 2020-03-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ZMessageOne implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private Integer type;

    private String message;

    @TableLogic
    private Integer status;

    @Version
    private Integer version;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
