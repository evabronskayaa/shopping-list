package com.example.shoppinglist;

import com.example.shoppinglist.form.ProductForm;
import com.example.shoppinglist.model.Product;
import com.example.shoppinglist.repository.ProductRepository;
import com.example.shoppinglist.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ModuleTests {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void addProduct(){
        Product savedProduct = new Product(new ProductForm("milk"));
        productService.save(savedProduct);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("milk");

        Mockito.verify(productRepository, Mockito.times(1)).save(savedProduct);
    }

    @Test
    public void deleteProduct(){
        Optional<Product> product = Optional.of(new Product(new ProductForm("milk")));
        Long id = product.get().getId();
        Mockito.doReturn(product).when(productRepository).findById(id);
        productService.remove(id);
        assertThat(product.get().getId()).isNull();
    }

    @Test
    public void updateProduct(){
        Product savedProduct = new Product(new ProductForm("cucumber"));
        productService.save(savedProduct);

        boolean bought = savedProduct.isBought();
        savedProduct.setBought(!bought);
        productService.save(savedProduct);
        Mockito.doReturn(Optional.of(savedProduct)).when(productRepository).findById(savedProduct.getId());
        productService.getById(savedProduct.getId()).ifPresent(product -> {
            assertThat(product.isBought()).isEqualTo(!bought);
        });
    }
}
