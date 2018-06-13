package com.kh.shopfiy.challenge.dao;

import java.util.List;

/**
 * This class represents Cart which includes a list of {@link CartItem}
 *
 * @author Hao Kuang
 * @date 2018/05/13
 */
public class Cart {

    private List<CartItem> cartItemList;

    public Cart(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }
}
