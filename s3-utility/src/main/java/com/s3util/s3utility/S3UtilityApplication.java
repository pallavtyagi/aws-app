package com.s3util.s3utility;

import org.apache.commons.cli.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@ComponentScan(basePackages = "com.s3util")
public class S3UtilityApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3UtilityApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(S3UtilityApplication.class, args).close();
	}
	private static String FILE_PATH = "filePath";
	private static String BUCKET_PREFIX = "bucketPrefix";

	@Autowired
	@Qualifier("awsS3Writer")
	private BiConsumer<String, String> awsS3Writer;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Application Started");
		System.out.println("Application Started");

		Map<String, String> inputOptions = getInputOptins(args);
		LOGGER.info("Uploading {} files to S3 Bucket with Prefix {}", inputOptions.get(FILE_PATH), inputOptions.get(BUCKET_PREFIX));

		awsS3Writer.accept(inputOptions.get(FILE_PATH), inputOptions.get(BUCKET_PREFIX));
		LOGGER.info("Files Uploaded SuccessFully.");

		System.out.println("Files Uploaded SuccessFully.");
		System.exit(0);
	}


	private Map<String, String> getInputOptins(String... args) {
		Options options = new Options();
		options.addOption("f",FILE_PATH,true, "Directory Path to upload to S3.")
		       .addOption("p", BUCKET_PREFIX, true, "Provide bucket prefix for your file upload.");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("s3-utility", options);

			System.exit(1);
		}

		Map<String, String> optionsMap = new HashMap<>(2);
		optionsMap.put(FILE_PATH, cmd.getOptionValue(FILE_PATH));
		optionsMap.put(BUCKET_PREFIX, cmd.getOptionValue(BUCKET_PREFIX));

		return optionsMap;

	}
}
