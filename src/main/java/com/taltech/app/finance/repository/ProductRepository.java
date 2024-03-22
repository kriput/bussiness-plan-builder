package com.taltech.app.finance.repository;

import com.taltech.app.finance.domain.Product;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {

    List<Product> findAllByFinancialForecast_Id(Long id);

}
