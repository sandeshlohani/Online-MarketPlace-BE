package edu.miu.cs545.waa.controller;

import edu.miu.cs545.waa.domain.Address;
import edu.miu.cs545.waa.dto.FilterDto;
import edu.miu.cs545.waa.dto.OrderDto;
import edu.miu.cs545.waa.enums.Payment;
import edu.miu.cs545.waa.service.OrderService;
import edu.miu.cs545.waa.util.ExcelGenerator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private OrderService orderService;

  @Autowired
  public void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public List<OrderDto> getAllOrders(FilterDto filterDto, Principal principal) {
    return orderService.getAllOrders(filterDto, principal.getName());
  }

  @GetMapping("{id}")
  public OrderDto getOrderById(@PathVariable Long id, Principal principal) {
    return orderService.getOrderById(id, principal.getName());
  }

  @PostMapping
  public List<OrderDto> createOrders(@RequestBody List<OrderDto> ordersDto, Principal principal) {
    return orderService.createOrders(ordersDto, principal.getName());
  }

  @PutMapping("/{id}")
  public OrderDto updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto, Principal principal) {
    return orderService.updateOrder(id, orderDto, principal.getName());
  }

  @PutMapping("/payments")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void makePayment(@RequestParam List<Long> ordersId, Principal principal) {
    orderService.makePayment(ordersId, principal.getName());
  }

  @PutMapping("/{id}/cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void cancelOrder(@PathVariable Long id, Principal principal) {
    orderService.cancelOrder(id, principal.getName());
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable Long id, Principal principal) {
    orderService.deleteOrder(id, principal.getName());
  }

  /**
   * @param orderId
   * @param userId
   * @return excel sheet
   * @throws IOException TODO: fields to print to be finalized
   */
  @GetMapping(value = "/generate-excel")
  public ResponseEntity<InputStreamResource> generateOrderExcel(@RequestParam long orderId,
      @RequestParam String userId) throws IOException {
    //for checking
    OrderDto orderDto = new OrderDto(Instant.now(), 2, new Address("one", "two", "three", "four"),
        new Address("one", "two", "three", "four"), Payment.DUE, 123L);
//    OrderDto orderDto = orderService.getOrderById(orderId, userId);
    ByteArrayInputStream in = ExcelGenerator.createExcelReport(orderDto);
    // return IOUtils.toByteArray(in);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition",
        "attachment; filename=order-details-" + new Date(System.currentTimeMillis()) + ".xlsx");

    return ResponseEntity
        .ok()
        .headers(headers)
        .body(new InputStreamResource(in));
  }

}
