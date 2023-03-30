package com.example.walletservice;

import com.example.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/*@SpringBootApplication
public class WalletServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}*/

@SpringBootApplication
public class WalletServiceApplication implements CommandLineRunner{

	@Autowired
	private WalletService walletService;

	@Override
	public void run(String[] args) throws Exception{
		walletService.loadData();
	}

	public static void main(String[] args){
		SpringApplication.run(WalletServiceApplication .class, args);
	}
}