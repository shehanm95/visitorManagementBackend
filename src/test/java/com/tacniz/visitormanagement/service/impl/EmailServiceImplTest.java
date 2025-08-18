package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.OptChecker;
import com.tacniz.visitormanagement.model.OTPObj;
import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.repo.OPTRepo;
import com.tacniz.visitormanagement.repo.UserEntityRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private OPTRepo optRepo;

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Captor
    private ArgumentCaptor<OTPObj> optObjCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendFourDigitAuthenticationEmail_validEmail_savesAndSendsEmail() {
        String email = "test@example.com";

        when(optRepo.findByEmail(email)).thenReturn(Optional.empty());
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mockMimeMessage);

        emailService.sendFourDigitAuthenticationEmail(email);

        verify(optRepo).save(optObjCaptor.capture());
        OTPObj saved = optObjCaptor.getValue();
        assertEquals(email, saved.getEmail());
        assertNotNull(saved.getDigits());
        assertEquals(4, saved.getDigits().length());

        verify(mailSender).send(mockMimeMessage);
    }

    @Test
    void testSendFourDigitAuthenticationEmail_nullEmail_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendFourDigitAuthenticationEmail(null);
        });
    }

    @Test
    void testCheckOpt_validCode_returnsTrue_andUpdatesUser() {
        String email = "user@example.com";
        String digits = "1234";

        OptChecker optChecker = OptChecker.builder()
                .email(email)
                .digits(digits)
                .build();

        OTPObj optObj = OTPObj.builder()
                .email(email)
                .digits(digits)
                .build();

        UserEntity user = UserEntity.builder()
                .email(email)
                .isEmailVerified(false)
                .build();

        when(optRepo.findByEmail(email)).thenReturn(Optional.of(optObj));
        when(userEntityRepository.findByEmail(email)).thenReturn(Optional.of(user));

        boolean result = emailService.checkOpt(optChecker);

        assertTrue(result);
        assertTrue(user.getIsEmailVerified());
        verify(optRepo).deleteByEmail(email);
        verify(userEntityRepository).save(user);
    }

    @Test
    void testCheckOpt_invalidCode_returnsFalse() {
        OptChecker optChecker = OptChecker.builder()
                .email("wrong@example.com")
                .digits("0000")
                .build();

        when(optRepo.findByEmail(optChecker.getEmail()))
                .thenReturn(Optional.of(OTPObj.builder().email("wrong@example.com").digits("9999").build()));

        boolean result = emailService.checkOpt(optChecker);

        assertFalse(result);
    }

    @Test
    void testCheckOpt_emailNotFound_returnsFalse() {
        OptChecker optChecker = OptChecker.builder()
                .email("noone@example.com")
                .digits("1234")
                .build();

        when(optRepo.findByEmail(optChecker.getEmail())).thenReturn(Optional.empty());

        boolean result = emailService.checkOpt(optChecker);

        assertFalse(result);
    }
}
