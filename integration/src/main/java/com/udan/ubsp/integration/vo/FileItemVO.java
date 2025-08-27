package com.udan.ubsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileItemVO {

	@Schema(description = "文件名称")
	private String fileName;

	@Schema(description = "文件大小(KB)")
	private long fileSize;
}