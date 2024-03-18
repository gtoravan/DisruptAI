package main.java.com.example.demo_api.controller;

import main.java.com.example.demo_api.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class api_controller {

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        return ResponseEntity.ok("OKK");
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }



}
