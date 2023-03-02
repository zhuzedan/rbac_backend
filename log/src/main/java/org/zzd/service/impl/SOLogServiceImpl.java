package org.zzd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zzd.entity.SystemOperationLog;
import org.zzd.mapper.SystemOperationLogMapper;
import org.zzd.service.SOLogService;

/**
 * @author :zzd
 * @apiNote :操作日志服务实现类
 * @date : 2023-03-02 15:46
 */
@Service
public class SOLogServiceImpl implements SOLogService {
    @Autowired
    private SystemOperationLogMapper systemOperationLogMapper;

    @Override
    public void saveSystemLog(SystemOperationLog sysOperationLog) {
        systemOperationLogMapper.insert(sysOperationLog);
    }
}
