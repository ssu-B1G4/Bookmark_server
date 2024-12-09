package B1G4.bookmark.apiPayload.exception.handler;

import B1G4.bookmark.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 헤더에서 JWT 추출
            String token = accessor.getFirstNativeHeader("Authorization");


            if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
                token = token.substring(7); // Bearer 부분을 제거

                try {
                    // JWT 인증 수행
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);

                    // StompAccessor에 사용자 정보를 추가 (STOMP 메시지 사용자 정보)
                    accessor.setUser(authentication);

                    // **SecurityContext에 인증 정보를 추가 (Spring Security에 인증 정보 추가)**
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);


                } catch (Exception e) {
                }
            } else {
            }
        }
        return message;
    }
}