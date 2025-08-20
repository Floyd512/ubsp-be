package com.udan.ubsp.integration.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udan.ubsp.common.enums.ResultCodeEnum;
import com.udan.ubsp.common.exception.UBSPException;
import com.udan.ubsp.integration.config.SeaTunnelConfig;
import com.udan.ubsp.integration.dto.SeaTunnelJobSubmitDTO;
import com.udan.ubsp.integration.service.SeaTunnelApiService;
import com.udan.ubsp.integration.vo.TaskExecutionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * @Description SeaTunnel API服务实现类
 * @Author TOM FORD
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SeaTunnelApiServiceImpl implements SeaTunnelApiService {

    private final SeaTunnelConfig seaTunnelConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public TaskExecutionVO submitJob(SeaTunnelJobSubmitDTO submitDTO) {
        try {
            // 直接构建URL，不进行编码处理
            StringBuilder urlBuilder = new StringBuilder(seaTunnelConfig.getBaseUrl() + "/submit-job?");
            
            // 添加查询参数，直接拼接中文
            if (submitDTO.getJobName() != null) {
                urlBuilder.append("jobName=").append(submitDTO.getJobName()).append("&");
            }
            if (submitDTO.getFormat() != null) {
                urlBuilder.append("format=").append(submitDTO.getFormat()).append("&");
            }
            if (submitDTO.getIsStartWithSavePoint() != null) {
                urlBuilder.append("isStartWithSavePoint=").append(submitDTO.getIsStartWithSavePoint()).append("&");
            }
            if (submitDTO.getJobId() != null) {
                urlBuilder.append("jobId=").append(submitDTO.getJobId()).append("&");
            }
            
            // 移除最后的&符号
            String url = urlBuilder.toString();
            if (url.endsWith("&")) {
                url = url.substring(0, url.length() - 1);
            }
            
            HttpHeaders headers = buildAuthHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAcceptCharset(java.util.Arrays.asList(StandardCharsets.UTF_8));
            
            // 请求体直接是SeaTunnel配置JSON，不包装在DTO中
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(submitDTO.getConfig(), headers);
            
            // 输出调试信息
            String configJson = objectMapper.writeValueAsString(submitDTO.getConfig());
            log.info("提交SeaTunnel作业，URL: {}", url);
            log.info("请求体JSON: {}", configJson);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("SeaTunnel作业提交失败，状态码: {}, 响应: {}", 
                         response.getStatusCode(), response.getBody());
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                      "SeaTunnel作业提交失败: " + response.getBody());
            }
            
            String responseBody = response.getBody();
            log.info("SeaTunnel作业提交响应: {}", responseBody);
            
            // 解析响应JSON
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, 
                    new TypeReference<Map<String, Object>>() {});
            
            // 检查SeaTunnel返回的是否为失败状态
            if ("fail".equals(responseMap.get("status"))) {
                String errorMessage = (String) responseMap.get("message");
                if (errorMessage == null || errorMessage.trim().isEmpty()) {
                    errorMessage = "SeaTunnel任务提交失败";
                }
                log.error("SeaTunnel返回失败状态: {}", errorMessage);
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, errorMessage);
            }
            
            // 检查是否包含必要的字段
            if (!responseMap.containsKey("jobId") || responseMap.get("jobId") == null) {
                log.error("SeaTunnel响应中缺少jobId字段: {}", responseMap);
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                      "SeaTunnel响应格式异常，缺少jobId");
            }
            
            TaskExecutionVO result = new TaskExecutionVO();
            result.setJobId(String.valueOf(responseMap.get("jobId")));
            result.setJobName(String.valueOf(responseMap.get("jobName")));
            
            log.info("任务提交成功，jobId: {}, jobName: {}", result.getJobId(), result.getJobName());
            
            return result;
            
        } catch (RestClientException e) {
            log.error("调用SeaTunnel API失败", e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "调用SeaTunnel API失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("提交SeaTunnel作业时发生异常", e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "提交作业时发生异常: " + e.getMessage());
        }
    }

    @Override
    public Object getJobInfo(String jobId) {
        try {
            String url = seaTunnelConfig.getBaseUrl() + "/job-info/" + jobId;
            
            HttpHeaders headers = buildAuthHeaders();
            HttpEntity<?> entity = new HttpEntity<>(headers);
            
            log.info("获取SeaTunnel作业信息，URL: {}", url);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("获取SeaTunnel作业信息失败，状态码: {}, 响应: {}", 
                         response.getStatusCode(), response.getBody());
                throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                      "获取作业信息失败: " + response.getBody());
            }
            
            String responseBody = response.getBody();
            log.info("SeaTunnel作业信息获取成功，响应: {}", responseBody);
            
            return objectMapper.readValue(responseBody, Object.class);
            
        } catch (RestClientException e) {
            log.error("调用SeaTunnel API失败", e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "调用SeaTunnel API失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("获取SeaTunnel作业信息时发生异常", e);
            throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_EXECUTION_FAILED, 
                                  "获取作业信息时发生异常: " + e.getMessage());
        }
    }

    /**
     * 构建认证请求头
     */
    private HttpHeaders buildAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        
        String authType = seaTunnelConfig.getAuthType();
        if (authType == null) {
            authType = "none";
        }
        
        switch (authType.toLowerCase()) {
            case "basic":
                // Basic Auth: Authorization: Basic base64(username:password)
                if (seaTunnelConfig.getUsername() != null && seaTunnelConfig.getPassword() != null) {
                    String credentials = seaTunnelConfig.getUsername() + ":" + seaTunnelConfig.getPassword();
                    String encodedCredentials = Base64.getEncoder()
                            .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
                    headers.set("Authorization", "Basic " + encodedCredentials);
                    log.debug("使用Basic认证，用户名: {}", seaTunnelConfig.getUsername());
                }
                break;
                
            case "bearer":
            case "token":
                // Bearer Token: Authorization: Bearer <token>
                if (seaTunnelConfig.getToken() != null) {
                    headers.set("Authorization", "Bearer " + seaTunnelConfig.getToken());
                    log.debug("使用Bearer Token认证");
                }
                break;
                
            case "custom":
                // Custom Header: <custom-header>: <token>
                if (seaTunnelConfig.getToken() != null && seaTunnelConfig.getAuthHeader() != null) {
                    headers.set(seaTunnelConfig.getAuthHeader(), seaTunnelConfig.getToken());
                    log.debug("使用自定义认证头: {}", seaTunnelConfig.getAuthHeader());
                }
                break;
                
            case "none":
            default:
                // 无认证
                log.debug("无需认证");
                break;
        }
        
        return headers;
    }
}
