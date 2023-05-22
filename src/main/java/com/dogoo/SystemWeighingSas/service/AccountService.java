package com.dogoo.SystemWeighingSas.service;

import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;

import java.util.List;

public interface AccountService {
    void createAccount(AccountMapperModel account);

    Account getAccountByScreenName(String screenNamme);
    Account getAccountByMail(String email);
    Account getAccountByKey(String key);
    Account getAccountByAccountId(Integer accountId);

    ResultResponse<Account> getAccounts(UserTokenModel model, Integer limit, Integer page);
    List<Account> getAccounts();

    void changePasswordAccount(AccountMapperModel account) throws Exception;

    void activeAccount(AccountMapperModel account) throws Exception;
}
