package com.taotao.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.bean.Cart;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.User;
import com.taotao.web.service.CartService;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;

@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    /**
     * 去订单确认页
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
        ModelAndView mv = new ModelAndView("order");
        mv.addObject("item", this.itemService.queryItemById(itemId));
        return mv;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView toCartOrder() {
        ModelAndView mv = new ModelAndView("order-cart");
        List<Cart> carts = this.cartService.queryCartList();
        if (carts.isEmpty()) {
            // TODO 提交用户出错，或者购物车为空
        }
        mv.addObject("carts", carts);
        return mv;
}

    /**
     * 提交订单
     * 
     * @param order
     * @param token
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitOrder(Order order) {
        // 添加当前登录用户的信息
        User user = UserThreadLocal.get();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());

        Map<String, Object> result = new HashMap<String, Object>();

        String orderId = this.orderService.submitOrder(order);
        if (null == orderId) {
            result.put("status", 400);
        } else {
            // 订单创建成功
            result.put("status", 200);
            result.put("data", orderId);
        }
        return result;
    }

    /**
     * 货到付款的成功页
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "success")
    public ModelAndView success(@RequestParam("id") String orderId) {
        ModelAndView mv = new ModelAndView("success");
        // 查询订单数据
        mv.addObject("order", this.orderService.queryOrderById(orderId));
        // 当前时间往后推2天
        mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }

}
