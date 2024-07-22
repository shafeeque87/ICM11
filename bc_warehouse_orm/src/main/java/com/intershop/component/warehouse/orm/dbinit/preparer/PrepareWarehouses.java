package com.intershop.component.warehouse.orm.dbinit.preparer;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.intershop.beehive.core.capi.domain.Domain;
import com.intershop.beehive.core.capi.domain.DomainMgr;
import com.intershop.beehive.core.capi.localization.LocaleInformation;
import com.intershop.beehive.core.capi.localization.LocaleMgr;
import com.intershop.beehive.core.dbinit.capi.Preparer;
import com.intershop.component.warehouse.internal.WarehousePO;
import com.intershop.component.warehouse.internal.WarehousePOAlternateKey;
import com.intershop.component.warehouse.internal.WarehousePOFactory;

public class PrepareWarehouses extends Preparer
{

    @Inject private DomainMgr domainMgr;
    @Inject private LocaleMgr localeMgr;
    @Inject private WarehousePOFactory factory;
        
    private LocaleInformation localeInformation =localeMgr.getLeadLocale();
    private String warehouseProperties = null;
    private ResourceBundle bundle;
       
    @Override
    public boolean checkParameters()
    {
        if(getNumberOfParameters() == 1)
        {
            warehouseProperties = getParameter(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean prepare()
    {
        try
        {
            bundle = getResourceBundle(warehouseProperties);
        }
        catch (MissingResourceException ex)
        {
//            Logger.error(this, "No input file found");
            return false;
        }
        
        String domainName = bundle.getString("DomainName");
        Domain domain = domainMgr.getDomainByName(domainName);
        if (domain == null)
        {
            return false;
        }
        
        int i = 0;
        boolean eof = false;
        
        while(!eof)
        {
            try
            {
                ++i;
                String warehouseName = bundle.getString("Warehouse." + i + ".Name");
                String warehouseLocation = bundle.getString("Warehouse." + i + ".Location");                
                String warehouseDescription = bundle.getString("Warehouse." + i + ".Description");
                       
                
                if(factory.getObjectByAlternateKey(new WarehousePOAlternateKey(warehouseName, domain.getDomainID())) == null)
                {
                    WarehousePO warehousePO = factory.create(domain, warehouseName);
                    warehousePO.setLocation(warehouseLocation);
                    warehousePO.setDescription(warehouseDescription, localeInformation);
                    // optional
                    warehousePO.setCapacity((int)(Math.random()*5000 +1));
                }
                else
                {
//                    Logger.error(this, "Warehouse {} already exist", warehouseName);
                }
                
            }
            catch (MissingResourceException ex)
            {
                eof = true;
            }
        }
        
        return true;
    }

}
