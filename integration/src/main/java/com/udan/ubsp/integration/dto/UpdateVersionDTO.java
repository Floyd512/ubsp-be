package com.udan.ubsp.integration.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 更新版本DTO
 * @Author TOM FORD
 */
@Data
@Schema(description = "更新版本DTO")
public class UpdateVersionDTO {

    @Schema(description = "任务ID", example = "1")
    private Long taskId;

    @Schema(description = "环境配置JSON")
    private JsonNode env;

    @Schema(description = "源端配置JSON")
    private JsonNode source;

    @Schema(description = "转换配置JSON")
    private JsonNode transforms;

    @Schema(description = "目标端配置JSON")
    private JsonNode sink;

    @Schema(description = "渲染后的配置文本")
    private String renderedText;

    @Schema(description = "渲染后的配置JSON")
    private JsonNode renderedJson;

    @Schema(description = "是否切换为当前版本", example = "true")
    private Boolean switchToCurrent;

    @Schema(description = "变更备注")
    private String changeMemo;
}