package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.OptChecker;

public interface EmailService {

    void sendFourDigitAuthenticationEmail(String toEmail);

    boolean checkOpt(OptChecker optChecker);
}
