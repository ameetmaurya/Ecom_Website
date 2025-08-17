
package com.example.Ecommerce.Controller;

import java.security.Principal;
import java.util.List;

import com.example.Ecommerce.Model.*;
import com.example.Ecommerce.Service.OrderService;
import com.example.Ecommerce.util.CommonUtil;
import com.example.Ecommerce.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.Service.CartService;
import com.example.Ecommerce.Service.CategoryService;
import com.example.Ecommerce.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OrderService orderService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {

        return "user/home";
    }

    @ModelAttribute
    public void getUserDetails(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            UserDtls userDtls = userService.getUserByEmail(email);
            m.addAttribute("user", userDtls);
            Integer countCart = cartService.getCountCart(userDtls.getId());
            m.addAttribute("countCart", countCart);
        }

        List<Category> allActiveCategory = categoryService.getAllActiveCategory();
        m.addAttribute("categorys", allActiveCategory);
    }

    @GetMapping("/addCart")
    public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
        Cart saveCart = cartService.saveCart(pid, uid);

        if (ObjectUtils.isEmpty(saveCart)) {
            session.setAttribute("errorMsg", "Product add to cart failed");
        } else {
            session.setAttribute("succMsg", "Product added to cart");
        }
        return "redirect:/product/" + pid;
    }

    @GetMapping("/cart")
    public String loadCartPage(Principal p, Model m) {

        UserDtls user = getLoggedInUserDetails(p);
        List<Cart> carts = cartService.getCartsByUser(user.getId());
        m.addAttribute("carts", carts);
        if (carts.size() > 0) {
            Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/user/cart";
    }

    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
        cartService.updateQuantity(sy, cid);
        return "redirect:/user/cart";
    }

    private UserDtls getLoggedInUserDetails(Principal p) {
        String email = p.getName();
        UserDtls userDtls = userService.getUserByEmail(email);
        return userDtls;
    }

    @GetMapping("/orders")
    public String orderPage(Principal p,Model m){
        UserDtls user = getLoggedInUserDetails(p);
        List<Cart> carts = cartService.getCartsByUser(user.getId());
        m.addAttribute("carts", carts);
        if (carts.size() > 0) {
            Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
            Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice()+250+100;
            m.addAttribute("orderPrice", orderPrice);
            m.addAttribute("totalOrderPrice", totalOrderPrice);
        }
        return "/user/order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute OrderRequest request,Principal p)throws Exception{
//        System.out.println(request);
        UserDtls user=getLoggedInUserDetails(p);
      orderService.saveOrder(user.getId(),request);
        return "redirect:/user/success";
    }

    @GetMapping("/success")
    public String loadSuccess(){
        return "/user/success";
    }


    @GetMapping("/user-orders")
    public String myorder(Model m,Principal p){
        UserDtls loginUser=getLoggedInUserDetails(p);
List<ProductOrder> orders=orderService.getOrderByUser(loginUser.getId());
m.addAttribute("orders",orders);
        return "/user/my_orders";
    }

    @GetMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer id,@RequestParam Integer st,HttpSession session){

  OrderStatus[] values=OrderStatus.values();
  String status=null;
  for(OrderStatus orderSt:values)
  {
      if(orderSt.getId().equals(st))
      {
          status=orderSt.getName();
      }
  }
  ProductOrder updateOrder=orderService.updateOrderStatus(id,status);

  try {
      commonUtil.sendMailForProductOrder(updateOrder, status);
  }catch (Exception e){
      e.printStackTrace();
  }

  if(!ObjectUtils.isEmpty(updateOrder))
  {
      session.setAttribute("succMsg","Status updated");
  }
  else{
      session.setAttribute("errorMsg","Status not updated");

  }
return "redirect:/user/user-orders";
    }
@GetMapping("/profile")
public String profile()
{
    return "/user/profile";
}

@PostMapping("/update-profile")
public String updateProfile(@ModelAttribute UserDtls user,@RequestParam MultipartFile img,HttpSession session)
{
   UserDtls updateUserProfile= userService.updateUserProfile(user, img);
    if(!ObjectUtils.isEmpty(updateUserProfile))
    {
            session.setAttribute("succMsg","Profile updated");
        }
        else{
            session.setAttribute("errorMsg","profile not updated ");

        }
return "redirect:/user/profile";
}

@PostMapping("/change-password")
public String changePassword(@RequestParam String newPassword,@RequestParam String currentPassword,Principal p,HttpSession session)
{
    UserDtls loggedInUserDetails=getLoggedInUserDetails(p);
 boolean matches= passwordEncoder.matches(currentPassword,loggedInUserDetails.getPassword());
  if(matches)
  {
         String encodPassword= passwordEncoder.encode(newPassword);
         loggedInUserDetails.setPassword(encodPassword);
         UserDtls updateUser=userService.updateUser(loggedInUserDetails);
         if(ObjectUtils.isEmpty(updateUser))
         {
             session.setAttribute("errorMsg","password is not updated || error in server");

         }
         else {
             session.setAttribute("succMsg"," password updated successfully");

         }
  }
  else{
      session.setAttribute("errorMsg","Current password is incorrect");

  }
    return "redirect:/user/profile";
}

}
