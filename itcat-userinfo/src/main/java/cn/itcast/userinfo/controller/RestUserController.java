package cn.itcast.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.userinfo.pojo.User;
import cn.itcast.userinfo.service.UserService;

@RequestMapping("rest/user")
@Controller
public class RestUserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户数据
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}/{name}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserById(@PathVariable("id") Long id,
            @PathVariable("name") String name) {
        try {
            User user = this.userService.queryUserById(id);
            if (null == user) {
                // 资源不存在，响应404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 资源存在，返回200
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出错，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 新增用户数据
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveUser(User user) {
        try {
            this.userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出错，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 更新用户数据
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user) {
        try {
            Integer count = this.userService.updateUser(user);
            if (count == 0) {
                // 更新的资源不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出错，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 删除用户数据
     * 
     * @param user
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            Integer count = this.userService.deleteById(id);
            if (count == 0) {
                // 删除的资源不存在
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 204
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 出错，响应500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
