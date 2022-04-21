package edu.miu.cs545.waa.service.impl;

import edu.miu.cs545.waa.domain.Order;
import edu.miu.cs545.waa.domain.Product;
import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.OrderDto;
import edu.miu.cs545.waa.dto.ProductDto;
import edu.miu.cs545.waa.dto.StatusDto;
import edu.miu.cs545.waa.enums.Status;
import edu.miu.cs545.waa.exception.DataNotFoundException;
import edu.miu.cs545.waa.exception.UnprocessableException;
import edu.miu.cs545.waa.repository.OrderRepository;
import edu.miu.cs545.waa.repository.ProductRepository;
import edu.miu.cs545.waa.service.OrderService;
import edu.miu.cs545.waa.service.ProductService;
import edu.miu.cs545.waa.service.SellerService;
import edu.miu.cs545.waa.util.CustomNullAwareBeanUtils;
import edu.miu.cs545.waa.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private MapperUtils<ProductDto> mapperToProductDto;
    private MapperUtils<Product> mapperToProduct;
    private MapperUtils<OrderDto> mapperToOrderDto;
    private OrderService orderService;
    private SellerService sellerService;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Autowired
    public void setMapperToProductDto(MapperUtils<ProductDto> mapperToProductDto) {
        this.mapperToProductDto = mapperToProductDto;
    }

    @Autowired
    public void setMapperToProduct(MapperUtils<Product> mapperToProduct) {
        this.mapperToProduct = mapperToProduct;
    }

    @Autowired
    public void setMapperToOrderDto(MapperUtils<OrderDto> mapperToOrderDto) {
        this.mapperToOrderDto = mapperToOrderDto;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public ProductDto getProductById(Long id, String userId) {
        return (ProductDto) mapperToProductDto.getMap(productRepository.findByIdAndUserId(id, userId), new ProductDto());
    }

    @Override
    public ProductDto getProductById(Long id) {
        ProductDto productDto = (ProductDto) mapperToProductDto.getMap(productRepository.getById(id), new ProductDto());
        productDto.setSellerDto(sellerService.getSeller(productDto.getUserId()));
        return productDto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProductDto> getAllProducts(FilterDto filterDto) {
        return (List<ProductDto>) mapperToProductDto.mapList(productRepository.findAll(), new ProductDto());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProductDto> getAllProductsForAUser(FilterDto filterDto, String userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        return (List<ProductDto>) mapperToProductDto.mapList(products, new ProductDto());
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, String userId) {
        Product product = (Product) mapperToProduct.getMap(productDto, new Product());
        product.setUserId(userId);
        product.setStatus(Status.CREATED);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, String userId) {
        Product user = (Product) mapperToProduct.getMap(productDto, new Product());
        Product currentProduct = productRepository.findByIdAndUserId(id, userId);
        if (currentProduct == null) {
            throw new DataNotFoundException(String.format("Product with id %d not found", id));
        }
        CustomNullAwareBeanUtils.myCopyProperties(user, currentProduct);
        productRepository.save(currentProduct);
        return productDto;
    }

    @Override
    public void deleteProduct(Long id, String userId) {
        Product currentProduct = productRepository.findByIdAndUserId(id, userId);
        if (currentProduct == null) {
            throw new DataNotFoundException(String.format("Product with id %d not found", id));

        }
        if (currentProduct.getStatus().equals(Status.PURCHASED)) {
            throw new UnprocessableException(String.format("Product with id %d already purchased.", id));
        }
        currentProduct.setStatus(Status.DELETED);
        productRepository.save(currentProduct);
    }

    @Override
    public List<OrderDto> getOrders(Long id, String userId) {
//        return getProductById(id, userId).getOrders().stream().filter(o -> o.getPayment().equals(Payment.PAID)).collect(Collectors.toList());
        return getProductById(id, userId).getOrders().stream().collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrderStatus(Long id, Long orderId, StatusDto statusDto, String userId) {
        Product product = productRepository.findByIdAndUserId(id, userId);
        if (product == null) {
            throw new DataNotFoundException(String.format("Product with id %d not found", id));
        }
        Optional<Order> order = product.getOrders().stream().filter(o -> o.getId().equals(orderId)).findFirst();
        if (order.isEmpty()) {
            throw new DataNotFoundException(String.format("Order with id %d not found", orderId));
        }
        order.get().setStatus(statusDto.getStatus());
        //increase the point of the buyer
        if (order.get().getStatus().equals(Status.DELIVERED)) {
            //TODO
//            increaseBuyerRewardPoint(order.get().getUserId());
        }
        return orderService.saveOrder(order.get());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderDto> getOrdersForAllProducts(String userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductDto> productsDto = (List<ProductDto>) mapperToProductDto.mapList(products, new ProductDto());
        return productsDto.stream().map(ProductDto::getOrders).flatMap(List::stream).collect(Collectors.toList());
    }


    @Override
    @SuppressWarnings("unchecked")
    public OrderDto getOrderById(Long orderId, String userId) {
        OrderDto order = (OrderDto) mapperToOrderDto.getMap(orderRepository.getById(orderId), new OrderDto());
        return order;
    }
}
