package com.ismail.products.web;


import com.ismail.products.dao.ProductRepository;
import com.ismail.products.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    public String index(Model model,@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Product> pageOfProducts = productRepository.findByDesignationContains(keyword,PageRequest.of(page,6));
        model.addAttribute("products",pageOfProducts.getContent());
        model.addAttribute("pages",new int[pageOfProducts.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "products";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id,int page,String keyword){
        productRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/formProduct")
    public String form(Model model){
        model.addAttribute("product",new Product());
        return "formProduct";
    }

    @PostMapping("/admin/save")
    public String save(Model model,@Valid Product product, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formProduct";
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model,Long id){
        Product product = productRepository.findById(id).get();
        model.addAttribute("product",product);
        return "editFormProduct";
    }
    @GetMapping("/")
    public String def(){
        return "redirect:/user/index";
    }

    @GetMapping("/403")
    public  String accessDenied(){
        return "403";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

}

