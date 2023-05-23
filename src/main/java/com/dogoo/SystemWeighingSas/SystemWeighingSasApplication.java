package com.dogoo.SystemWeighingSas;

import com.dogoo.SystemWeighingSas.enumEntity.RoleEnum;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;

@SpringBootApplication
public class SystemWeighingSasApplication implements CommandLineRunner {

	private final AccountService accountService;

	public SystemWeighingSasApplication(AccountService accountService) {
		this.accountService = accountService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SystemWeighingSasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        if (accountService.getAccountByScreenName("admin_dogoo") == null) {
            AccountMapperModel account = new AccountMapperModel();
            account.setName("admin dogoo");
            account.setCreateDate(new Timestamp(System.currentTimeMillis()));
            account.setScreenName("admin_dogoo");
            account.setRole(RoleEnum.admin.name());
            account.setPhoneNumber("");
            account.setStatus("active");
            account.setEmail("trungnb@dogoo.com.vn");
            account.setPassword("dogoo");
            accountService.createAccount(account);
        }
	}
}
