package com.intershop.sellside.rest.stock.v1.internal.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.intershop.component.application.capi.CurrentApplicationBOProvider;
import com.intershop.component.product.capi.ProductBO;
import com.intershop.component.product.capi.ProductBORepository;
import com.intershop.component.product.capi.ProductBORepositoryExtension;
import com.intershop.component.warehouse.capi.StockBO;
import com.intershop.component.warehouse.capi.WarehouseBO;
import com.intershop.component.warehouse.capi.WarehouseBORepository;
import com.intershop.sellside.rest.stock.v1.capi.handler.StockHandler;

public class StockHandlerImpl implements StockHandler
{

    @Inject
    private CurrentApplicationBOProvider currentApplicationBOProvider;

    @Override
    public Collection<StockBO> getStockBOs(String productSKU)
    {

        WarehouseBORepository warehouseBORepository = currentApplicationBOProvider.get()
                        .getRepository(WarehouseBORepository.EXTENSION_ID);

        ProductBORepository productBORepository = currentApplicationBOProvider.get()
                        .getRepository(ProductBORepositoryExtension.EXTENSION_ID);

        ProductBO productBO = productBORepository.getProductBOBySKU(productSKU);

        String productID = productBO.getID();

        Collection<WarehouseBO> warehouseBOs = warehouseBORepository.getWarehouseBOs();
        List<StockBO> stockBOs = new ArrayList<>();
        for (WarehouseBO warehouseBO : warehouseBOs)
        {
            StockBO stockBO = warehouseBO.getStockBO(productID);
            if (null != stockBO)
            {
                stockBOs.add(stockBO);
            }
        }
        return stockBOs;
    }

}
