package com.taotao.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.HttpResult;
import com.taotao.common.service.ApiService;
import com.taotao.web.bean.Order;

@Service
public class OrderService {

    @Value("${ORDER_TAOTAO_URL}")
    private String ORDER_TAOTAO_URL;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 调用订单系统的接口完成订单的提交
     * 
     * @param order
     * @return 如果成功返回订单号，失败，返回null
     */
    public String submitOrder(Order order) {
        String url = ORDER_TAOTAO_URL + "/order/create";
        try {
            HttpResult httpResult = this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
            if (httpResult.getCode() == 200) {
                JsonNode jsonNode = MAPPER.readTree(httpResult.getData());
                if (jsonNode.get("status").asInt() == 200) {
                    // 订单创建成功
                    return jsonNode.get("data").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据订单id查询订单数据
     * 
     * @param orderId
     * @return
     */
    public Order queryOrderById(String orderId) {
        String url = ORDER_TAOTAO_URL + "/order/query/" + orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (null != jsonData) {
                return MAPPER.readValue(jsonData, Order.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
