package com.udan.ubsp.integration.dto;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description 渲染配置DTO
 * @Author TOM FORD
 */
@Data
@Schema(description = "渲染配置DTO")
public class RenderConfigDTO {

    @Schema(description = "环境配置JSON")
    private JsonNode env;

    @Schema(description = "源端配置JSON")
    private JsonNode source;

    @Schema(description = "转换配置JSON")
    private JsonNode transforms;

    @Schema(description = "目标端配置JSON")
    private JsonNode sink;

    @Schema(description = "渲染格式", example = "JSON/HOCON")
    private String format;
}