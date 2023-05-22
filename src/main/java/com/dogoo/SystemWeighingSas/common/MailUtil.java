package com.dogoo.SystemWeighingSas.common;

import javax.mail.MessagingException;

public interface MailUtil {

    String sendSimpleMail(String email, String msgBody, String subject) throws MessagingException;
}
