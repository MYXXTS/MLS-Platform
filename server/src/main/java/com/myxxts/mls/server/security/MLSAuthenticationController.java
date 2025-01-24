package com.myxxts.mls.server.security;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myxxts.mls.server.model.common.HttpResponse;
import com.myxxts.mls.server.model.consts.MLSConst;
import com.myxxts.mls.server.security.entity.MLSAuthenticationDto;
import com.myxxts.mls.server.security.entity.MLSAuthenticationVo;
import com.myxxts.mls.server.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping (MLSConst.PathPrefix.AUTH)
@RequiredArgsConstructor
public class MLSAuthenticationController {

  private final UserService userService;

  @PostMapping ("/register")
  public HttpResponse<MLSAuthenticationVo> register (@Valid @RequestBody MLSAuthenticationDto mlsAuthenticationDto) {
    userService.createUser(mlsAuthenticationDto);
    return HttpResponse.success("Register Success.");
  }

  @PostMapping ("/login")
  public HttpResponse<MLSAuthenticationVo> login (@RequestBody MLSAuthenticationDto mlsAuthenticationDto) {
    return null;
  }

}
