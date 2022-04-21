package edu.miu.cs545.waa.service;

import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.OrderDto;
import edu.miu.cs545.waa.dto.ProductDto;
import edu.miu.cs545.waa.dto.StatusDto;

import java.util.List;

public interface ProductService {
    ProductDto getProductById(Long id, String userId);

    ProductDto getProductById(Long id);

    List<ProductDto> getAllProducts(FilterDto filterDto);

    List<ProductDto> getAllProductsForAUser(FilterDto filterDto, String userId);

    ProductDto createProduct(ProductDto productDto, String userId);

    ProductDto updateProduct(Long id, ProductDto productDto, String userId);

    void deleteProduct(Long id, String userId);

    List<OrderDto> getOrders(Long id, String userId);

    OrderDto updateOrderStatus(Long id, Long orderId, StatusDto statusDto, String userId);

    List<OrderDto> getOrdersForAllProducts(String userId);

    OrderDto getOrderById(Long orderId, String userId);
}
