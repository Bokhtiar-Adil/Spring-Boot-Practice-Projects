package com.springprac.database;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class DatabaseApplication implements CommandLineRunner {

	public final DataSource dataSrc;

	public DatabaseApplication(final DataSource dataSrc) {
		this.dataSrc = dataSrc;
	}
	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Datasrc: " + dataSrc.toString());
		final JdbcTemplate resttmplt = new JdbcTemplate(dataSrc);
		resttmplt.execute("select 1");
	}
}
