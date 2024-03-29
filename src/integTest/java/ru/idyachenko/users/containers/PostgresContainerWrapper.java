package ru.idyachenko.users.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerWrapper extends PostgreSQLContainer<PostgresContainerWrapper> {
    private static final String POSTGRES_IMAGE_NAME = "postgres:14";
    private static final String POSTGRES_DB = "user";
    private static final String POSTGRES_USER = "postgres-user";
    private static final String POSTGRES_PASSWORD = "postgres-password";

    public PostgresContainerWrapper() {
        super(POSTGRES_IMAGE_NAME);
        this
                // if you need container logger
                // .withLogConsumer(new Slf4jLogConsumer(log))
                .withDatabaseName(POSTGRES_DB)
                .withUsername(POSTGRES_USER)
                .withPassword(POSTGRES_PASSWORD)
                .withInitScript("postgres/init-db-schema-integTest.sql");
        // .withCopyFileToContainer(MountableFile.forClasspathResource("source_on_host"),
        // "destination_in_container");
    }

    @Override
    public void start() {
        super.start();
        this.getContainerId();
        // debug point. Container has to be already started
    }
}
