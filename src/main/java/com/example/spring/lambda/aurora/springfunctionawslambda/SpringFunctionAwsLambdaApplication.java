package com.example.spring.lambda.aurora.springfunctionawslambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rdsdata.RdsDataClient;
import software.amazon.awssdk.services.rdsdata.model.ExecuteStatementRequest;

import java.util.function.Supplier;

@SpringBootApplication
public class SpringFunctionAwsLambdaApplication {


	@Bean
	public Supplier<String> getCustomers(){
		return () -> {
			RdsDataClient client = RdsDataClient.builder().region(Region.US_EAST_1).build();

			String resourceArn = "arn:aws:rds:us-east-1:231909950768:cluster:my-retail-database";
			String secretArn = "arn:aws:secretsmanager:us-east-1:231909950768:secret:rds-db-credentials/cluster-SW6JGVVX2IGVBHAM53CKQAVHKA/postgres/1677243823204-b4e5or";
			String database = "my_retail_database_app";
			String sqlStatement = "SELECT * FROM Customers";
			ExecuteStatementRequest sqlRequest = ExecuteStatementRequest.builder()
					.resourceArn(resourceArn)
					.secretArn(secretArn)
					.database(database)
					.sql(sqlStatement)
					.build();
			return client.executeStatement(sqlRequest).toString();
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringFunctionAwsLambdaApplication.class, args);
	}

}
