package com.springboot.hello.data.repository.support;

import com.springboot.hello.data.entity.Product;
import com.springboot.hello.data.entity.QProduct;
import lombok.Builder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findByName(String name) {
        QProduct qProduct = QProduct.product;

        List<Product> productList = from(qProduct)
                .where(qProduct.name.eq(name))
                .select(qProduct)
                .fetch();

        return productList;
    }

}
