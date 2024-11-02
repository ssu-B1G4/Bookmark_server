package B1G4.bookmark.web.controller;

import B1G4.bookmark.apiPayload.BaseResponse;
import B1G4.bookmark.domain.Member;
import B1G4.bookmark.security.handler.annotation.AuthUser;
import B1G4.bookmark.service.MemberService.MemberService;
import B1G4.bookmark.web.dto.MemberDTO.AuthResponseDTO;
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

    @Operation(summary = "카카오 로그인 API", description = "카카오 로그인 및 회원 가입을 진행하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/login")
    public BaseResponse<AuthResponseDTO.OAuthResponse> kakaoLogin(@RequestParam("code") String code) {
        return BaseResponse.onSuccess(memberService.kakaoLogin(code));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "회원탈퇴 API", description = "회원을 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "COMMON200", description = "삭제 성공")
    })
    @DeleteMapping("/delete")
    public BaseResponse<String> delete(@Parameter(name = "user", hidden = true) @AuthUser Member member) {
        return BaseResponse.onSuccess(memberService.deleteMember(member));
    }
}
