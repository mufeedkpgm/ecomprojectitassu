package com.itassu.ecomproject.services.customer;

import java.util.List;

import com.itassu.ecomproject.dto.ProductDto;

public interface CustomerProductService {
	
	List<ProductDto> getAllProducts();
	
	List<ProductDto> searchProductsByTitle(String name);

}
