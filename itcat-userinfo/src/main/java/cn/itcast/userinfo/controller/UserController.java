package cn.itcast.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.userinfo.bean.EasyUIResult;
import cn.itcast.userinfo.pojo.User;
import cn.itcast.userinfo.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userServce;

    @RequestMapping(value = "query/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User queryUserById(@PathVariable("id") Long id) {
        return this.userServce.queryUserById(id);
    }

    @RequestMapping(value = "query/page/list", method = RequestMethod.GET)
    @ResponseBody
    public EasyUIResult queryUserPageList(@RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "3") Integer rows) {
        return this.userServce.queryUserPageList(page, rows);
    }

}
