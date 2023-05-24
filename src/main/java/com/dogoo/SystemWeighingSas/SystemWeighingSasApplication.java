package com.dogoo.SystemWeighingSas;

import com.dogoo.SystemWeighingSas.dao.IAccountDao;
import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.enumEntity.RoleEnum;
import com.dogoo.SystemWeighingSas.enumEntity.StatusEnum;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;

@SpringBootApplication
public class SystemWeighingSasApplication implements CommandLineRunner {

	private final IAccountDao iAccountDao;

    public SystemWeighingSasApplication(IAccountDao iAccountDao) {
        this.iAccountDao = iAccountDao;
    }

    public static void main(String[] args) {
		SpringApplication.run(SystemWeighingSasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        if (iAccountDao.findAccountByScreenName("admin_dogoo") == null) {
            Account account = new Account();
            account.setName("admin dogoo");
            account.setCreateDate(new Timestamp(System.currentTimeMillis()));
            account.setScreenName("admin_dogoo");
            account.setRole(RoleEnum.admin);
            account.setPhoneNumber("");
            account.setStatus(StatusEnum.active);
            account.setEmail("trungnb@dogoo.com.vn");
            account.setPassword("dogoo");
            iAccountDao.save(account);
        }
	}
}
