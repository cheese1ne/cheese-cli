package com.cheese.boot.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * mock用户数据，用户数据通过配置进行加载
 *
 * @author sobann
 */
@Data
@ConfigurationProperties(prefix = "cheese.user")
public class CheeseUserDetailsProperties {

    private List<String> userDetails = new ArrayList<>();
}
