package com.dogoo.SystemWeighingSas.validator;

import com.dogoo.SystemWeighingSas.dao.IAccountDao;
import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.enumEntity.StatusEnum;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.Response;
import com.dogoo.SystemWeighingSas.unitity.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountValidator {

    @Autowired
    private AccountService accountService;
    @Autowired
    private IAccountDao iAccountDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Response validatorScreenName(String screenName) {
        Account account = accountService.getAccountByScreenName(screenName);

        if (account == null) {
            return null;
        }
        return ResponseFactory.getClientErrorResponse("Tên đăng nhập đã được sử dụng");
    }

    public Response validatorScreenNameUpdate(long accountId, String screenName) {
        Account account = accountService.getAccountByScreenName(screenName);

        if (account == null) {
            return null;
        }
        if (account.getAccountId() == accountId)
            return null;

        return ResponseFactory.getClientErrorResponse("Tên đăng nhập đã được sử dụng");
    }

    public Response validatorEmail(String email) {
        Account account = accountService.getAccountByMail(email);

        if (account == null) {
            return null;
        }
        return ResponseFactory.getClientErrorResponse("Email đã được sử dụng");
    }

    public Response validatorEmailUpdate(long accountId, String email) {
        Account account = accountService.getAccountByMail(email);

        if (account == null) {
            return null;
        }
        if (account.getAccountId() == accountId)
            return null;

        return ResponseFactory.getClientErrorResponse("Email đã được sử dụng");
    }

    public Response validatorKey(String keyModel,
                                 String key) {
        Account account = accountService.getAccountByKey(key);

        if (account == null) {
            return null;
        }
        if (keyModel.equals(account.getKey())) {
            return null;
        }
        return ResponseFactory.getClientErrorResponse("key đã được sử dụng");
    }

    public Response validatorAdd(UserTokenModel model,
                                 AccountMapperModel accountDto) {
        Response responseScreenName = validatorScreenName(accountDto.getScreenName());
        Response responseEmail = validatorEmail(accountDto.getEmail());

        if (responseScreenName != null) {
            return responseScreenName;
        }

        return responseEmail;

    }

    public Response validatorUpdate(long id,
                                    UserTokenModel model,
                                    AccountMapperModel accountDto) {
        Response responseScreenName = validatorScreenNameUpdate(id, accountDto.getScreenName());
        Response responseEmail = validatorEmailUpdate(id, accountDto.getEmail());

        if (responseScreenName != null) {
            return responseScreenName;
        }

        return responseEmail;
    }

    public Response validatorScreenNameLogin(String screenName) {

        Account account = iAccountDao.findAccountByScreenName(
                screenName);

        if (account != null) {
            return null;
        }
        return ResponseFactory.getClientErrorResponse("Tên đăng nhập hoặc mật khẩu không chính xác");
    }

    public Response validatorPassword(String screenName, String password) {

        Account account = iAccountDao.findAccountByScreenName(
                screenName);

        if (passwordEncoder.matches(password, account.getPassword())) {
            return null;
        }
        return ResponseFactory.getClientErrorResponse("Tên đăng nhập hoặc mật khẩu không chính xác");
    }

    public Response validatorStatus(String screenName) {

        Account account = iAccountDao.findAccountByScreenName(screenName);

        if (account.getStatus().equals(StatusEnum.active)) {
            return null;
        }

        return ResponseFactory.getClientErrorResponse("Tài khoản đã bị khóa");
    }

    public Response validatorLogin(String screenName, String password) {

        Response responseScreenName = validatorScreenNameLogin(screenName);
        Response responsePassword = validatorPassword(screenName, password);
        Response responseStatus = validatorStatus(screenName);

        if (responseScreenName != null) {
            return responseScreenName;
        }

        if (responsePassword != null) {
            return responsePassword;
        }

        return responseStatus;

    }

    public Response validatorOldPassword(Account account, String oldPassword) {
        if (!account.getChangePassword())
            return null;

        if (passwordEncoder.matches(oldPassword, account.getPassword()))
            return null;

        return ResponseFactory.getClientErrorResponse("Mật khẩu cũ không đúng");
    }

    public Response validatorConfirmPassword(String newPassword,
                                             String confirmPassword) {
        if (newPassword.equals(confirmPassword))
            return null;

        return ResponseFactory.getClientErrorResponse("Mật khẩu cũ không đúng");
    }

    public Response validatorChangePassword(long accountId,
                                            AccountMapperModel model){

        Account account = iAccountDao.findByAccountId(accountId);
        Response responseOldPassword = validatorOldPassword(account, model.getPassword());
        Response responseConfirmPassword = validatorConfirmPassword(
                model.getNewPassword(), model.getConfirmPassword());

        if (responseOldPassword != null) {
            return responseOldPassword;
        }

        return responseConfirmPassword;
    }
}
