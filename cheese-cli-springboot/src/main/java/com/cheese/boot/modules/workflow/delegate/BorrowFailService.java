package com.cheese.boot.modules.workflow.delegate;

import com.cheese.boot.core.workflow.delegate.BaseServiceTaskDelegate;
import com.cheese.boot.core.workflow.service.IProcessConfigService;
import com.cheese.boot.core.workflow.service.ITaskConfigService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * 借阅失败后归档服务任务
 *
 * @author sobann
 */
@Slf4j
@Service("borrowFailService")
public class BorrowFailService extends BaseServiceTaskDelegate {

    public BorrowFailService(ObjectProvider<IProcessConfigService> processConfigServiceProvider,
                             ObjectProvider<ITaskConfigService> taskConfigServiceProvider) {
        super(processConfigServiceProvider.getIfAvailable(), taskConfigServiceProvider.getIfAvailable());
    }

    @Override
    protected void sync(DelegateExecution execution) {
    }

    @Override
    protected void init(DelegateExecution execution) {

    }

    @Override
    protected void log(DelegateExecution execution) {
        log.error("borrow failed");
    }

    @Override
    protected void extra(DelegateExecution execution) {

    }
}
