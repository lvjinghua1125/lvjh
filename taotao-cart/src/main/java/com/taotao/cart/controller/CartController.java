package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartCookieService cartCookieService;

    /**
     * 商品加入到购物车
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        // 判断用户是否登录
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录
            this.cartCookieService.addItemToCart(itemId, request, response);
        } else {
            // 已登录
            this.cartService.addItemToCart(itemId);
        }

        return "redirect:/show.html";
    }

    /**
     * 显示购物车列表
     * 
     * @return
     */
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public ModelAndView showCartList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cart");
        // 判断用户是否登录
        User user = UserThreadLocal.get();
        List<Cart> carts = null;
        if (null == user) {
            // 未登录
            carts = this.cartCookieService.queryCartList(request);
        } else {
            // 已登录
            carts = this.cartService.queryCartList();
        }
        mv.addObject("cartList", carts);
        return mv;
    }

    /**
     * 根据用户id查询购物车列表
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "query/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> queryCartList(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(this.cartService.queryCartList(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 从购物车中删除商品
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "delete/{itemId}", method = RequestMethod.GET)
    public String deleteItemFromCart(@PathVariable("itemId") Long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        // 判断用户是否登录
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录
            this.cartCookieService.deleteItemToCart(itemId, request, response);
        } else {
            // 已登录
            this.cartService.deleteItemToCart(itemId);
        }

        return "redirect:/show.html";
    }

    /**
     * 修改购买商品的数量
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "cart/update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateItemFromCart(@PathVariable("itemId") Long itemId,
            @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 判断用户是否登录
        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录
            this.cartCookieService.updateItemToCart(itemId, num, request, response);
        } else {
            // 已登录
            this.cartService.updateItemToCart(itemId, num);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
