# testcontainers-config
Setting up a project with testcontainer configuration


~~~
singleton container + flyway inicializado manualmente

/**
 * <p>Classe base para os testes de integração.</p>
 * <p>Inicia um único container pra rodar todos os testes.</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTestBase {

    static final PostgreSQLContainer container;

    static {
        container = new PostgreSQLContainer("postgres:15-alpine");
        if (!container.isRunning()) {
            container.start();
        }
        Flyway flyway = setupFlyway(container);
        flyway.migrate();
    }

    protected static final DatabaseDelegate databaseDelegate = new JdbcDatabaseDelegate(container, "");

    @Autowired
    protected MockMvc mockMvc;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    private static Flyway setupFlyway(PostgreSQLContainer container) {
        return Flyway.configure()
                .schemas("schema_name")
                .locations("db.migration")
                .dataSource(container.getJdbcUrl(), container.getUsername(), container.getPassword())
                .load();
    }

    @BeforeAll
    public static void setupTestMass() {
        ScriptUtils.runInitScript(databaseDelegate, "insert-data.sql");
    }

}
~~~
