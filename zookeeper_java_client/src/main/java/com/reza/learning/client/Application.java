package com.reza.learning.client;

import com.reza.learning.client.zookeeper.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {


    @Value("${app.client.zookeeper.cluster}")
    private String cluster;

    @Value("${app.client.zookeeper.znode}")
    private String znode;

    @Value("${app.client.zookeeper.filename}")
    private String filename;

    @Value("${app.client.zookeeper.exec}")
    private String[] exec;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");
        log.info("Connecting to znode: '{}', on cluster '{}' to write to '{}'", znode, cluster, filename);

        try {
            Executor executor = new Executor(cluster, znode, filename, exec);
            executor.run();
        } catch (Exception e) {
            log.info("Error occurred");
            throw new RuntimeException(e);
        }
        log.info("Executed!");

    }
}