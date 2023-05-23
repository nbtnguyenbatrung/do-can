package com.dogoo.SystemWeighingSas.service.impl;

import com.dogoo.SystemWeighingSas.common.MailUtil;
import com.dogoo.SystemWeighingSas.common.generalPassword.PwdGenerator;
import com.dogoo.SystemWeighingSas.config.Constants;
import com.dogoo.SystemWeighingSas.dao.IAccountDao;
import com.dogoo.SystemWeighingSas.entity.Account;
import com.dogoo.SystemWeighingSas.entity.Customer;
import com.dogoo.SystemWeighingSas.enumEntity.RoleEnum;
import com.dogoo.SystemWeighingSas.enumEntity.StatusEnum;
import com.dogoo.SystemWeighingSas.model.AccountMapperModel;
import com.dogoo.SystemWeighingSas.model.UserTokenModel;
import com.dogoo.SystemWeighingSas.service.AccountService;
import com.dogoo.SystemWeighingSas.unitity.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final IAccountDao iAccountDao;
    private final PasswordEncoder passwordEncoder;
    private final PwdGenerator pwdGenerator;
    private final MailUtil mailUtil;
    private final String SUBJECT = "[Dogoo Can] - THƯ THÔNG TIN TÀI KHOẢN TRÊN DOGOO CAN";

    public AccountServiceImpl(IAccountDao iAccountDao,
                              PasswordEncoder passwordEncoder,
                              PwdGenerator pwdGenerator, MailUtil mailUtil) {
        this.iAccountDao = iAccountDao;
        this.passwordEncoder = passwordEncoder;
        this.pwdGenerator = pwdGenerator;
        this.mailUtil = mailUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String screenName) throws UsernameNotFoundException {
        Account account = iAccountDao.findAccountByScreenName(screenName);
        if (account == null) {
            logger.error("account not found in the database");
            throw new UsernameNotFoundException("account not found in the database");
        } else {
            logger.info("account found in the database: {}", screenName);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().name()));

        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), authorities);
    }

    @Override
    public void createAccount(AccountMapperModel accountMapperModel) {
        Account account = Constants.SERIALIZER.convertValue(accountMapperModel, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setChangePassword(Boolean.FALSE);
        account.setCreateDate(new Timestamp(System.currentTimeMillis()));
        iAccountDao.save(account);
    }

    @Override
    public Account getAccountByScreenName(String screenName) {
        return iAccountDao.findAccountByScreenName(screenName);
    }

    @Override
    public Account getAccountByMail(String email) {
        return iAccountDao.findAccountByEmail(email);
    }

    @Override
    public Account getAccountByKey(String key) {
        return iAccountDao.findAccountByKey(key);
    }

    @Override
    public Account getAccountByAccountId(Integer accountId) {
        return iAccountDao.fetchAccountByAccountId(accountId);
    }

    @Override
    public ResultResponse<Account> getAccounts(UserTokenModel model,
                                               Integer limit,
                                               Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(page, 30, sort);
        Page<Account> accounts = iAccountDao.findAll(pageable);
        if (!model.getKey().equals("")){
            accounts = iAccountDao.findAllCustomer(model.getKey(), pageable);
        }
        Long total = accounts.getTotalElements();
        ResultResponse<Account> resultResponse = new ResultResponse<>();
        resultResponse.setData(accounts.getContent());
        resultResponse.setLimit(limit);
        resultResponse.setPage(page);
        resultResponse.setTotal(total);
        resultResponse.setTotalPage((int) (total / limit));
        return resultResponse;
    }

    @Override
    public List<Account> getAccounts() {
        return iAccountDao.findAll();
    }

    @Override
    public void changePasswordAccount(AccountMapperModel account) throws Exception {

    }

    @Override
    public void activeAccount(AccountMapperModel account) throws Exception {

    }

    @Override
    public void createAccountCustomer(Customer customer) throws MessagingException {

        String password = pwdGenerator.getPassword();
        Account account = new Account();
        account.setName(customer.getCustomerName());
        account.setCreateDate(new Timestamp(System.currentTimeMillis()));
        account.setScreenName(pwdGenerator.getScreeName(customer.getCustomerName(), null));
        account.setRole(RoleEnum.adminUser);
        account.setPhoneNumber(customer.getPhoneNumber());
        account.setStatus(StatusEnum.active);
        account.setEmail(customer.getEmail());
        account.setPassword(passwordEncoder.encode(password));
        account.setChangePassword(Boolean.FALSE);
        account = iAccountDao.save(account);

        Map<String, Object> model = new HashMap<>();
        model.put("name", customer.getCustomerName());
        model.put("screenName", account.getScreenName() );
        model.put("password", account.getPassword() );
        mailUtil.sendSimpleMail(customer.getEmail(),
                SUBJECT , model );

    }
}
