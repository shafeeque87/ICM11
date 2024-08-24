package com.intershop.rest.promotions.handler;

import java.util.Collections;
import java.util.Iterator;

import com.google.inject.Inject;
import com.intershop.beehive.core.capi.domain.PersistentObjectBOExtension;
import com.intershop.beehive.core.capi.domain.PersistentObjectPO;
import com.intershop.beehive.core.capi.localization.provider.CurrentLocaleProvider;
import com.intershop.component.customer.capi.CustomerBO;
import com.intershop.component.customer.capi.provider.CurrentCustomerBOProvider;
import com.intershop.component.customer.orm.internal.orm.CustomerPO;
import com.intershop.component.product.capi.ProductBO;
import com.intershop.component.promotion.capi.PromotionBO;
import com.intershop.sellside.rest.common.internal.PromotionHandlerImpl;

public class CustomPromotionhandlerImpl extends PromotionHandlerImpl
{

    @Inject
    CurrentCustomerBOProvider currentCustomerBOProvider;
    
    @Inject 
    CurrentLocaleProvider currentLocaleProvider;

    @Override
    public Iterator<PromotionBO> getPromotionsForProduct(ProductBO productBO, boolean includeCodePromotions)
    {

        String curentLocale = currentLocaleProvider.getCurrentLocale().getLocaleID();
        
        CustomerBO customerBO = currentCustomerBOProvider.getCurrentCustomerBO();
        if (null != customerBO)
        {
            PersistentObjectBOExtension poext = customerBO.getExtension(PersistentObjectBOExtension.class);
            String customerCountry = null;
            if (poext != null && (poext.getPersistentObject() instanceof PersistentObjectPO))
            {
                CustomerPO customer = poext.getPersistentObject();

                customerCountry = customer.getString("Country");
            }
            if (customerCountry == null)
            {
                return super.getPromotionsForProduct(productBO, includeCodePromotions);
            }
            if ((customerCountry.equals(curentLocale)))
            {
                return super.getPromotionsForProduct(productBO, includeCodePromotions);
            }
            else
            {
                return Collections.emptyIterator();
            }

        }
        else
        {
            return super.getPromotionsForProduct(productBO, includeCodePromotions);
        }
        
        
    
    
    }
}
