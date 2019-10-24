package com.sprint.dash.auth.controller;

import com.sprint.dash.common.exception.RException;
import com.sprint.dash.common.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class SecurityController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @DeleteMapping("signout")
    public R signout(HttpServletRequest request) throws RException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        R r = new R();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new RException("退出登录失败");
        }
        return r.put("msg", "退出登录成功");
    }

}
