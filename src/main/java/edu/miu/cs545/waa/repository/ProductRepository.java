package edu.miu.cs545.waa.repository;

import edu.miu.cs545.waa.domain.Product;
import edu.miu.cs545.waa.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByIdAndUserId(Long id, String userId);

    public List<Product> findAllByUserId(String userId);

}
