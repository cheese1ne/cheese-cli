package com.cheese.boot.modules.workflow.listener;

import com.cheese.boot.core.workflow.listener.BaseTaskListener;
import com.cheese.core.tool.util.Func;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * countersign 并行任务中任务参数检查
 *
 * @author sobann
 */
@Service
public class ConfirmCompleteListener extends BaseTaskListener {

    @Override
    protected void checkVariables(Map<String, Object> variables) {
        if (variables.get("confirmSts") != null) {
            int isPass = Func.toInt(variables.get("confirmSts"), 0);
            if (isPass == 0) {
                // 保存状态，在redis中保存一个驳回标识
                System.out.println(" 确认驳回，打回");
            }
        }
    }
}
