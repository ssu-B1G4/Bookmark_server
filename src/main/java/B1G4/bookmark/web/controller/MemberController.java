package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.apiPayload.code.status.SuccessStatus;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.MemberService.MemberService;
import B1G4.bookmark.web.dto.MemberDTO.AuthRequestDTO;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
import B1G4.bookmark.web.dto.MemberDTO.MemberResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 및 회원 가입을 진행하는 API입니다.")
    @GetMapping("/login")
    public BaseResponse<AuthResponseDTO.OAuthResponse> kakaoLogin(@RequestParam("code") String code) {
        return BaseResponse.of(SuccessStatus.USER_LOGIN_OK, memberService.kakaoLogin(code));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "회원탈퇴 API", description = "회원을 삭제하는 API입니다.")
    @DeleteMapping("/delete")
    public BaseResponse<String> deleteMember(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        memberService.deleteMember(member);
        return BaseResponse.of(SuccessStatus.USER_DELETE_OK, null );
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(
            summary = "JWT Access Token 재발급 API",
            description = "Refresh Token을 검증하고 새로운 Access Token과 Refresh Token을 응답합니다.")
    @PostMapping("/refresh")
    public BaseResponse<AuthResponseDTO.TokenRefreshResponse> refresh(@RequestBody AuthRequestDTO.RefreshTokenDTO request) {
        return BaseResponse.of(SuccessStatus.USER_REFRESH_OK, memberService.refresh(request.getRefreshToken()));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "마이페이지 API", description = "마이페이지의 회원 정보를 가져오는 API입니다.")
    @GetMapping("/mypage")
    public BaseResponse<MemberResponseDTO.MyPageResponse> getMyPageInfo(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        return BaseResponse.of(SuccessStatus.MYPAGE_OK, memberService.getMyPageInfo(member));
    }
}
