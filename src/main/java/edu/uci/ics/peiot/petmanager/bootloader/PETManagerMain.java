package edu.uci.ics.peiot.petmanager.bootloader;

import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "edu.uci.ics.peiot.petmanager.webinterface", "edu.uci.ics.peiot.petmanager.service"})
public class PETManagerMain {

	public static void main(String[] args) {

		// // Tell server to look for petmanager.properties or
		// // petmanager.yml (could specify profile name in the file name)
		System.setProperty("spring.config.name", "petmanager");

		MDC.put("logFileName", "petmanager");

		SpringApplication.run(PETManagerMain.class, args);
	}
}