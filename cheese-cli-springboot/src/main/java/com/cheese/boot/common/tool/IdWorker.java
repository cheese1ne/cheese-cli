package com.cheese.boot.common.tool;

import com.cheese.core.tool.util.Func;

/**
 * 主键工具类
 *
 * @author sobann
 */
public class IdWorker {

    private IdWorker() {
    }

    private final static SnowflakeIdGenerator SNOWFLAKE_ID_GENERATOR = new SnowflakeIdGenerator();

    /**
     * 雪花id
     *
     * @return
     */
    public static long nextId() {
        return SNOWFLAKE_ID_GENERATOR.nextId();
    }

    /**
     * 雪花id 字符串
     *
     * @return
     */
    public static String nextIdStr() {
        long id = SNOWFLAKE_ID_GENERATOR.nextId();
        return Func.toStr(id);
    }

    /**
     * UUID
     *
     * @return
     */
    public static String uuid() {
        return Func.randomUUID();
    }

}
