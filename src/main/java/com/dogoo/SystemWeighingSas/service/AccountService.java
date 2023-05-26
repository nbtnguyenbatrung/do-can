package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

import javax.mail.MessagingException;
import java.util.List;

public interface AccountService {
    void createAccount(UserTokenModel tokenModel, AccountMapperModel account);

    Account getAccountByScreenName(String screenNamme);
    Account getAccountByMail(String email);
    Account getAccountByKey(String key);
    AccountMapperModel getAccountByAccountId(long accountId);

    ResultResponse<Account> getAccounts(UserTokenModel model, Integer limit, Integer page);
    List<Account> getAccounts();

    void changePasswordAccount(long accountId, AccountMapperModel model);

    void activeAccount(long accountId, AccountMapperModel account);

    void createAccountCustomer(Customer customer) throws MessagingException;

    void updateAccount(long accountId, AccountMapperModel model);

    void changeRole(long accountId, AccountMapperModel model);

    String getScreenName(UserTokenModel model, String name);
}
