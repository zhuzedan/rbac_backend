package org.zzd.service;

import org.springframework.stereotype.Service;
import org.zzd.entity.SystemOperationLog;

/**
 * @author :zzd
 * @apiNote :操作日志服务接口
 * @date : 2023-03-02 15:46
 */
public interface SOLogService {
    void saveSystemLog(SystemOperationLog sysOperationLog);
}
