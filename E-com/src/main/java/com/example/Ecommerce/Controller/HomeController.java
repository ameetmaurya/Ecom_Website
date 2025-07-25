package com.example.Ecommerce.Controller;

import com.example.Ecommerce.Model.Category;
import com.example.Ecommerce.Model.Product;
import com.example.Ecommerce.Model.UserDtls;
import com.example.Ecommerce.Repository.ProductRepository;
import com.example.Ecommerce.Service.CategoryService;
import com.example.Ecommerce.Service.ProductService;
import com.example.Ecommerce.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    private CategoryService categoryService;

   @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/signin")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/product/{id}")
    public String view_product(@PathVariable int id, Model m){
        Product productById = productService.getProductById(id);
        m.addAttribute("product", productById);
        return "view_product";
    }

    @GetMapping("/products")
    public String products(Model m,@RequestParam(value = "category", defaultValue = "")String category){
        //System.out.println("category ="+category );
   List<Category> categories = categoryService.getAllActiveCategory();
 List<Product> products = productService.getAllActiveProducts(category);
  m.addAttribute("categories",categories);
        m.addAttribute("products",products);
        m.addAttribute("paramValue",category);
        return "product";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException
    {
        String imageName=file.isEmpty() ? "default.jpg": file.getOriginalFilename();
        user.setProfileImage(imageName);
        UserDtls saveUser=userService.saveUser(user);
        if(!ObjectUtils.isEmpty(saveUser))
        {
             if(!file.isEmpty())
             {
                 File saveFile = new ClassPathResource("static/img").getFile();
                 Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"profile_img"+File.separator+file.getOriginalFilename());

                // System.out.println(path);
                 Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
             }
            session.setAttribute("succMsg","Saved Successfully");
        }
        else{
            session.setAttribute("errorMsg","Something wrong on server");
        }
        return "redirect:/register";
    }

}
