package com.intershop.component.warehouse.capi;

import com.intershop.beehive.businessobject.capi.BusinessObject;
import com.intershop.beehive.core.capi.localization.LocaleInformation;

public interface WarehouseBO extends BusinessObject
{
    String getName();
    void setName(String name);
    String getLocation();
    void setLocation(String location);
    int getCapacity();
    void setCapacity(int capacity);
    String getDescription();
    String getDescription(LocaleInformation localeInformation);
    void setDescription(String description, LocaleInformation localeInformation);
    
    StockBO createStockBO(String productID, int count);
    StockBO updateStockBO(String productID, int count);
    StockBO getStockBO(String productID);
    void removeStockBO(String productID);

}
