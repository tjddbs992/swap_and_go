package com.swapandgo.sag.service.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String to, String code){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jangeh303127@gmail.com");
            message.setTo(to);
            message.setSubject("[SwapAndGo] 이메일 인증 코드");
            message.setText(
                    "안녕하세요.\n\n" +
                    "회원가입을 위한 인증코드는 다음과 같습니다 : \n\n" + code + "\n\n" +
                            "이 코드는 5분간 유효합니다. \n"
            );
            mailSender.send(message);
            log.info("인증 이메일 발송 완료 {}", to);
        } catch (Exception e) {
            log.error("인증 이메일 발송 실패 {}: {}", to, e.getMessage());
        }
    }
}
