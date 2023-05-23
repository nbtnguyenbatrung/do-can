package com.dogoo.SystemWeighingSas.common.generalPassword;

import com.dogoo.SystemWeighingSas.dao.IAccountDao;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class PwdGenerator {

    @Autowired
    private IAccountDao iAccountDao;

    public String getPassword(){
        PasswordGenerator gen = new PasswordGenerator();

        CharacterRule LCR = new CharacterRule(EnglishCharacterData.LowerCase);
        LCR.setNumberOfCharacters(7);

        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(7);

        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(7);

        return gen.generatePassword(21, LCR, UCR, DR);
    }

    public String getScreeName(String fullNameCustomer, String fullName){

        if (fullName == null){
            return getScreenNameAccountAdmin(fullNameCustomer.equals("") ? "d" : fullNameCustomer);
        }

        return getScreenNameAccountUser(fullNameCustomer.equals("") ? "d" : fullNameCustomer,
                fullName.equals("") ? "d" : fullName);
    }

    private String getScreenNameAccountAdmin(String fullNameCustomer ){

        String s = fullNameCustomer.trim();
        int lastIndex = s.lastIndexOf(" ");
        List<String> list = Arrays.asList(s.split(" ", lastIndex));
        String sc = list.stream()
                .map(s1 -> s1.substring(0,1).toLowerCase())
                .collect(Collectors.joining(""));
        String screenName = "admin_"+sc;
        boolean check = iAccountDao.existsByScreenName(screenName);
        int count = 1;
        AtomicReference<String> sbString = new AtomicReference<>(screenName);

        while (Boolean.TRUE.equals(check)){
            sbString.set(screenName + count);
            check = iAccountDao.existsByScreenName(sbString.get());
            count++;
        }
        return sbString.get();
    }

    private String getScreenNameAccountUser(String fullNameCustomer, String fullName){

        String s = fullNameCustomer.trim();
        int lastIndex = s.lastIndexOf(" ");
        List<String> list = Arrays.asList(s.split(" ", lastIndex));
        String sc = list.stream()
                .map(s1 -> s1.substring(0,1).toLowerCase())
                .collect(Collectors.joining(""));

        String s1 = fullName.trim();
        int lastIndex1 = s1.lastIndexOf(" ");
        List<String> list1 = Arrays.asList(s.split(" ", lastIndex1));
        String sc1 = list1.stream()
                .map(s2 -> s2.substring(0,1).toLowerCase())
                .collect(Collectors.joining(""));
        String screenName = sc + "_"+ sc1;

        boolean check = iAccountDao.existsByScreenName(screenName);
        int count = 1;
        AtomicReference<String> sbString = new AtomicReference<>(screenName);

        while (Boolean.TRUE.equals(check)){
            sbString.set(screenName + count);
            check = iAccountDao.existsByScreenName(sbString.get());
            count++;
        }
        return sbString.get();
    }

}
