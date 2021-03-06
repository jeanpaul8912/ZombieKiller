package edu.puj.pattern_design.zombie_killer;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;

@SpringBootApplication
@ComponentScan(basePackages = "edu.puj.pattern_design.zombie_killer.*")
public class ZombieKillerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(
                ZombieKillerGUI.class)
                .headless(false).run(args);
        EventQueue.invokeLater(() -> {
            ZombieKillerGUI execution = ctx.getBean(ZombieKillerGUI.class);
            execution.setVisible(true);
        });
    }

}
