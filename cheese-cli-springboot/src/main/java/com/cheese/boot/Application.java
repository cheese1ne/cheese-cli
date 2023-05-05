package com.cheese.boot;

import org.springframework.boot.CheeseApplication;
import org.springframework.boot.autoconfigure.CheeseBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * launch入口
 *
 * @author sobann
 */
@CheeseBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = CheeseApplication.run(Application.class, args);
    }
}
