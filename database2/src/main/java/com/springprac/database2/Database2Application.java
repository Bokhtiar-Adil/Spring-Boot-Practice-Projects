package com.springprac.database2;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class Database2Application implements CommandLineRunner {

	public final DataSource ds;

	public Database2Application (final DataSource ds) {
		this.ds = ds;
	}

	public static void main(String[] args) {
		SpringApplication.run(Database2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Datasource: " + ds.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(ds);
		restTemplate.execute("select 1");
	}
}
