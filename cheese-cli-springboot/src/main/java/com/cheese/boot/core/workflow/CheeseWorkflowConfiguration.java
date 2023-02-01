package com.cheese.boot.core.workflow;

import com.cheese.boot.common.tool.IdWorker;
import com.cheese.boot.core.workflow.expore.WorkflowResourceConfig;
import com.cheese.core.tool.util.Func;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * activity工作流引擎配置类，可以使用META-INF/spring.factories进行装配
 * 工作流配置默认开启，可以通过配置activity.enabled = false关闭
 *
 * @author sobann
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "activity.enabled", matchIfMissing = true)
public class CheeseWorkflowConfiguration implements ProcessEngineConfigurationConfigurer {

    public final ActivitiEventListener activitiEventListener;

    public CheeseWorkflowConfiguration(ActivitiEventListener activitiEventListener) {
        this.activitiEventListener = activitiEventListener;
    }

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        //设置流程事件监听器
        List<ActivitiEventListener> eventListeners = springProcessEngineConfiguration.getEventListeners();
        if (Func.isEmpty(eventListeners)) {
            eventListeners = new ArrayList<>();
        }
        eventListeners.add(activitiEventListener);
        springProcessEngineConfiguration.setEventListeners(eventListeners);
        //设置主键生成器
        springProcessEngineConfiguration.setIdGenerator(IdWorker::nextIdStr);
    }


    @Bean
    public WorkflowResourceConfig workflowResourceConfig(){
        log.info("prepare to initialize WorkflowResource, expose the Jersey endpoint");
        return new WorkflowResourceConfig();
    }
}
