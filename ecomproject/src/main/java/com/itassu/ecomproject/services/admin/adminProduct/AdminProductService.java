package com.itassu.ecomproject.services.admin.adminProduct;

import java.io.IOException;
import java.util.List;

import com.itassu.ecomproject.dto.ProductDto;

public interface AdminProductService {
	
	ProductDto addProduct(ProductDto productDto) throws IOException;
	
	List<ProductDto> getAllProducts();
	
	List<ProductDto> getAllProductsByName(String name);
	
	boolean deleteProduct(Long id);

}
