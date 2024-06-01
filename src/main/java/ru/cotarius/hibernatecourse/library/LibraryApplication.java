package ru.cotarius.hibernatecourse.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@PropertySource({"classpath:application.yaml"})
@EnableScheduling
public class LibraryApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(LibraryApplication.class, args);

	}

}
