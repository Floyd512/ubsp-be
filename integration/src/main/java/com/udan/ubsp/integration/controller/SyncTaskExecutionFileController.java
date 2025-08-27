package com.udan.ubsp.integration.controller;

import com.udan.ubsp.integration.config.SeaTunnelConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Tag(name = "任务执行文件")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
@RequestMapping("/api/integration/task-exec/files")
public class SyncTaskExecutionFileController {

	private final SeaTunnelConfig seaTunnelConfig;

	@Operation(summary = "按文件名下载输出文件")
	@GetMapping("download")
	public void download(@RequestParam String filename, HttpServletResponse resp) throws IOException {
		Path root = Paths.get(seaTunnelConfig.getOutputRoot());
		Path target = root.resolve(filename).normalize();
		if (!target.startsWith(root) || !Files.exists(target) || Files.isDirectory(target)) {
			resp.sendError(404);
			return;
		}
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, StandardCharsets.UTF_8) + "\"");
		resp.setHeader("Content-Length", String.valueOf(Files.size(target)));
		try (var in = Files.newInputStream(target); var out = resp.getOutputStream()) {
			in.transferTo(out);
		}
	}
}


