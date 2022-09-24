package com.demo.liqui.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ClassLoaderResourceAccessor;

@RestController
public class ExecuteLiquibaseController {

	private static final String DB_CHANGELOG = "/db/changelog/changeMaster.xml";

	@Autowired
	DataSource dataSource;

	@Bean
	public SpringLiquibase liquibase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setShouldRun(false);
		return liquibase;
	}

	@GetMapping("/execute")
	public String executeLiquibase() throws Exception {

		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://shopwaala-db.cohbqg7h5gjl.us-east-2.rds.amazonaws.com:3306/liquibase_db", "admin",
				"Alzohar123#!");
		Database database = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquibase1 = new liquibase.Liquibase("/db/changelog/changeMaster.xml",
				new ClassLoaderResourceAccessor(), database);
		liquibase1.update(new Contexts(), new LabelExpression());
		return "Lquibase executed succcessfullly";
	}
}
