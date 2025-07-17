package com.udan.bdsp.integration.controller;

import com.udan.bdsp.integration.service.SyncDataSourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 同步数据源Controller
 * @Author TOM FORD
 * @Date 2025-07-16 13:42:49
 */
@Tag(name = "02-数据集成")
@RestController
@RequestMapping("/api/integration")
public class SyncDataSourceController {

    @Autowired
    private SyncDataSourceService sourceService;


}