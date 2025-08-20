package com.udan.ubsp.integration.service;

import com.udan.ubsp.integration.dto.SeaTunnelJobSubmitDTO;
import com.udan.ubsp.integration.vo.TaskExecutionVO;

/**
 * @Description SeaTunnel API服务接口
 * @Author TOM FORD
 */
public interface SeaTunnelApiService {

    /**
     * 提交作业到SeaTunnel
     * @param submitDTO 提交DTO
     * @return 执行结果
     */
    TaskExecutionVO submitJob(SeaTunnelJobSubmitDTO submitDTO);

    /**
     * 获取作业信息
     * @param jobId 作业ID
     * @return 作业信息
     */
    Object getJobInfo(String jobId);
}
