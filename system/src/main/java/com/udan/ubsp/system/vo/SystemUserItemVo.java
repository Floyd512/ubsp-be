package com.udan.ubsp.system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.udan.ubsp.system.entity.SystemUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 用户基本信息VO
 * @Author TOM FORD
 * @Date 2025-07-12 16:51:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户基本信息VO")
public class SystemUserItemVo extends SystemUserEntity {

    @Schema(description = "部门名称")
    @TableField(value = "department_name")
    private String DepartmentName;
}