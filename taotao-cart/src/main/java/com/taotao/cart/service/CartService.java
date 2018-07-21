package com.taotao.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.pojo.Item;
import com.taotao.cart.threadlocal.UserThreadLocal;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId) {
        // 判断该商品是否已经存在购物车中
        // 如果存在，数量相加
        // 如果不存在，加入到购物车
        Cart record = new Cart();
        record.setUserId(UserThreadLocal.get().getId());
        record.setItemId(itemId);
        Cart cart = this.cartMapper.selectOne(record);

        if (null == cart) {
            // 该商品在购物车中不存在

            // 查询商品数据
            Item item = this.itemService.queryItemById(itemId);

            cart = new Cart();
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cart.setId(null);
            cart.setItemId(itemId);
            cart.setItemImage(item.getImages()[0]);
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
            cart.setNum(1);// TODO
            cart.setUserId(UserThreadLocal.get().getId());
            this.cartMapper.insert(cart);
        } else {
            // 该商品已经存在购物车中
            cart.setNum(cart.getNum() + 1);// TODO:默认为1
            cart.setUpdated(new Date());
            this.cartMapper.updateByPrimaryKey(cart);
        }

    }

    public List<Cart> queryCartList() {
        return this.queryCartList(UserThreadLocal.get().getId());
    }
    
    public List<Cart> queryCartList(Long userId) {
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", userId);
        example.setOrderByClause("created DESC");// 按照需求来做
        return this.cartMapper.selectByExample(example);
    }

    /**
     * 删除当前登录用户的商品
     * 
     * @param itemId
     */
    public void deleteItemToCart(Long itemId) {
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(UserThreadLocal.get().getId());
        this.cartMapper.delete(record);
    }

    /**
     * 更新商品数据量
     * 
     * @param itemId
     * @param num 最终购买的商品数量
     */
    public void updateItemToCart(Long itemId, Integer num) {
        Cart record = new Cart();
        record.setNum(num);
        record.setUpdated(new Date());

        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", UserThreadLocal.get().getId())
                .andEqualTo("itemId", itemId);
        this.cartMapper.updateByExampleSelective(record, example);
    }

}
