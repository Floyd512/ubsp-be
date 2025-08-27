package com.udan.ubsp.integration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileSummaryVO {

	@Schema(description = "总大小(字节)")
	private long totalSize;

	@Schema(description = "文件列表")
	private List<FileItemVO> files;
}


