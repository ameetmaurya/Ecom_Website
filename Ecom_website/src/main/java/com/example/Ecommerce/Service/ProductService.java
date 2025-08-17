package com.example.Ecommerce.Service;

import com.example.Ecommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Boolean deleteProduct(Integer id);

    public Product getProductById(Integer id);
    public Product updateProduct(Product product, MultipartFile file) throws IOException;

    public List<Product> getAllActiveProducts(String category);

    public List<Product> searchProduct(String ch);

    public Page<Product> getAllActiveProductPagination(Integer pageNo,Integer pageSize,String category);

    public Page<Product> searchProductPagination(String ch,Integer pageNo,Integer pageSize);

    public Page<Product> getAllProductsPagination(Integer pageNo,Integer pageSize);

//    Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize);

    Page<Product> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch);
}
