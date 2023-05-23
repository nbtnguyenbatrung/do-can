package com.dogoo.SystemWeighingSas.common;

import javax.mail.MessagingException;
import java.util.Map;

public interface MailUtil {

    void sendSimpleMail(String email,
                        String subject,
                        Map<String, Object> model ) throws MessagingException;
}
