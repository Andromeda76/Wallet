package com.example.keywowallet.access;


import com.example.keywowallet.behaviors.StockResourceManagement;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Service
public class ResourceManagementAccess {

    private final BlockingQueue<StockResourceManagement> stockResourceManagements;

    public ResourceManagementAccess(StockResourceManagement stockResourceManagement) {
        this.stockResourceManagements = new LinkedBlockingQueue<>();
        stockResourceManagements.offer(stockResourceManagement);
    }

    public StockResourceManagement getResourceManagements() {
        System.out.println("resource management access remaining capacity: " + stockResourceManagements.remainingCapacity());
        return stockResourceManagements.poll();
    }


}
