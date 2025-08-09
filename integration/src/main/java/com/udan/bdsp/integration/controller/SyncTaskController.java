package com.udan.bdsp.integration.controller;

import com.udan.bdsp.integration.service.SyncTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 同步任务 Controller
 * @Author TOM FORD
 * @Date 2025-08-05 17:12:52
 */
@Tag(name = "任务管理")
@RestController
@RequestMapping("/api/integration")
public class SyncTaskController {

    @Autowired
    private SyncTaskService taskService;
}