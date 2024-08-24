package com.intershop.rest.catalog.handler;

import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import com.intershop.beehive.core.capi.domain.Domain;
import com.intershop.beehive.core.capi.domain.PersistentObjectBOExtension;
import com.intershop.beehive.core.capi.domain.PersistentObjectPO;
import com.intershop.beehive.core.capi.localization.provider.CurrentLocaleProvider;
import com.intershop.component.catalog.capi.CatalogCategoryBO;
import com.intershop.component.customer.capi.CustomerBO;
import com.intershop.component.customer.capi.provider.CurrentCustomerBOProvider;
import com.intershop.component.customer.orm.internal.orm.CustomerPO;
import com.intershop.sellside.rest.common.internal.CategoryHandlerImpl;

public class CustomCategoryHandlerImpl extends CategoryHandlerImpl
{

    @Inject
    CurrentCustomerBOProvider currentCustomerBOProvider;

    @Inject
    CurrentLocaleProvider currentLocaleProvider;

    @Override
    public List<CatalogCategoryBO> getRootCategories(Domain channelDomain, Domain siteDomain)
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
                return super.getRootCategories(channelDomain, siteDomain);
            }
            if ((customerCountry.equals(curentLocale)))
            {
                return super.getRootCategories(channelDomain, siteDomain);
            }
            else
            {
                return Collections.emptyList();
            }

        }
        else
        {
            return super.getRootCategories(channelDomain, siteDomain);
        }
    }
}
