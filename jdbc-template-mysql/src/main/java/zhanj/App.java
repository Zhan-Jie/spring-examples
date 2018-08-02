package zhanj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner{
    private Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    JdbcTemplate template;

    @Override
    public void run(String... args) throws Exception {
        log.info("create tables");
        template.execute("DROP TABLE IF EXISTS customer");
        template.execute("CREATE TABLE customer (`id` INT AUTO_INCREMENT PRIMARY KEY , `first_name` VARCHAR(255), `last_name` VARCHAR(255))");
        List<Object[]> names = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream().map(name -> name.split(" ")).collect(Collectors.toList());
        names.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
        template.batchUpdate("INSERT INTO customer(first_name, last_name) VALUES (?, ?)", names);
        log.info("querying for customer records where first_name = 'Josh' :");
        template.query("SELECT id, first_name, last_name FROM customer WHERE first_name = ?", new Object[]{"Josh"}, (rs, rowNum)->new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))).forEach(customer -> log.info(customer.toString()));
    }
}
