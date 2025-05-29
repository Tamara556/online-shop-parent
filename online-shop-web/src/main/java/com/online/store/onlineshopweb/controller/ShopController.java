package com.online.store.onlineshopweb.controller;

import com.online.store.onlineshopcommon.entity.Product;
import com.online.store.onlineshopcommon.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShopController {

    @Autowired
    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    private ProductService productService;

    @GetMapping("/shop")
    public String getShopPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "shop";
    }


}
