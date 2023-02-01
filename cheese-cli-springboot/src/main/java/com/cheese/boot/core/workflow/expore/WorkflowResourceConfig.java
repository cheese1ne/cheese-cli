package com.cheese.boot.core.workflow.expore;

import com.cheese.boot.core.workflow.common.constant.WorkflowUriConstant;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * jersey资源注册配置
 *
 * @author sobann
 */
@ApplicationPath(WorkflowUriConstant.BASE_URI)
public class WorkflowResourceConfig extends ResourceConfig {

    public WorkflowResourceConfig() {
        register(WorkflowEndpointResource.class);
    }
}
