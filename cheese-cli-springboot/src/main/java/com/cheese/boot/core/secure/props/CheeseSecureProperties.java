package com.cheese.boot.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全属性配置
 *
 * @author sobann
 */
@Data
@ConfigurationProperties(prefix = "cheese.secure")
public class CheeseSecureProperties {

    private final List<String> skipUrl = new ArrayList();

    private final List<String> skipOther = new ArrayList<>();
}
