package com.intershop.component.warehouse.capi;

import com.intershop.beehive.businessobject.capi.BusinessObject;

public interface StockBO extends BusinessObject
{
    int getCount();
    void setCount(int count);
    String getProductID();
    String getWarehouseID();
    String getWarehouseName();    
}


