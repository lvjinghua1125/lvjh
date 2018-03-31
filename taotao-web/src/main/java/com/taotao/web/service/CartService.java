package com.taotao.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.Cart;
import com.taotao.web.threadlocal.UserThreadLocal;

@Service
public class CartService {

    @Value("${CART_TAOTAO_URL}")
    private String CART_TAOTAO_URL;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> queryCartList() {
        try {
            String url = CART_TAOTAO_URL + "/service/query/" + UserThreadLocal.get().getId();
            String jsonData = this.apiService.doGet(url);
            return MAPPER.readValue(jsonData,
                    MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<Cart>();
    }

}
