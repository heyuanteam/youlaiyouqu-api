package spring.controller;

import spring.dto.BaseCommonResult;
import spring.dto.request.MemberRequest;
import spring.dto.result.UserLoginResponse;
import spring.member.service.MemberService;
import spring.model.UUserMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Logger
@Api(tags = "/memberCenter/members", description = "会员管理")
@RequestMapping("/memberCenter/members")
public class MemberController {
    @Autowired
    private MemberService userService;

    @ApiOperation("注册用户")
    @RequestMapping(value = "/member", method = RequestMethod.POST)
    @ResponseBody
    public BaseCommonResult<UUserMember> register(@RequestBody MemberRequest record) {
        return userService.register(record);
    }

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login/{code}", method = RequestMethod.GET)
    @ResponseBody
    public BaseCommonResult<UserLoginResponse>  login(@PathVariable String code) {
        return userService.login(code);
    }

    @ApiOperation(value = "登出")
    @RequestMapping(value = "/login/{code}", method = RequestMethod.DELETE)
    @ResponseBody
    public BaseCommonResult loginOut(@PathVariable String code) {
        return userService.loginOut(code);
    }
    @ApiOperation(value = "鱿费查询")
    @RequestMapping(value = "/money/{code}", method = RequestMethod.GET)
    @ResponseBody
    public BaseCommonResult getMoney(@PathVariable String code) {
        return userService.getMoney(code);
    }

}