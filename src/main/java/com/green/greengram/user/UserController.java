package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "회원 가입")
    public ResultResponse<Integer> signUP(@RequestPart(required = false) MultipartFile pic, @RequestPart UserSignUpReq p) {
        return ResultResponse.<Integer>builder()
                .resultMessage("가입 완료")
                .resultData(service.postSignUp(pic, p))
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq q) {
        UserSignInRes res=service.postSignIn(q);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();
    }

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject UserInfoGetReq q) {
        log.info("UserController>getUserInfo>q: {}", q);
        return ResultResponse.<UserInfoGetRes>builder()
                .resultData(service.GetUserInfo(q))
                .resultMessage("유저 프로필 정보")
                .build();
    }

    @PatchMapping("pic") //부분수정은 PatchMapping, 전체수정은 PutMapping 한다.
    public ResultResponse<String> patchProfilePic(@ModelAttribute UserPicPatchReq p){
        log.info("UserController>patchProfilePic>p: {}", p);
        return ResultResponse.<String>builder()
                .resultMessage("프로필 사진 변경 완료")
                .resultData(service.patchUserPic(p))
                .build();
    }
}
