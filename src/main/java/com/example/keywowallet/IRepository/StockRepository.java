package com.example.keywowallet.IRepository;

import com.example.keywowallet.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock, Integer> {
}
