package org.example.P1_TP3_JEE;


import org.example.P1_TP3_JEE.entities.Patient;
import org.example.P1_TP3_JEE.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class P1Tp3JeeApplication implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(P1Tp3JeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inserting data...");
		//patientRepository.save(new Patient(null,"youness",new Date(),false,34));
		//patientRepository.save(new Patient(null,"zakaria",new Date(),false,44));
		//patientRepository.save(new Patient(null,"fati",new Date(),false,54));
	}

}