package com.intershop.sellside.rest.stock.v1.capi;

import com.intershop.sellside.rest.common.v1.capi.CommonConstantsREST.IgnoreLocalizationTest;

import jakarta.ws.rs.core.MediaType;

public final class StockConstantsREST
{

    /**
     * A {@link String} constant representing the media type for V1 coupon requests.
     */
    @IgnoreLocalizationTest
    public static final String MEDIA_TYPE_STOCK_V1_JSON = "application/vnd.intershop.stock.v1+json";

    /**
     * Media type for V1 site coupon.
     */
    public static final MediaType MEDIA_TYPE_STOCK_V1_JSON_TYPE = new MediaType("application",
                    "vnd.intershop.stock.v1+json");

    /**
     * Path for V1 coupon resources.
     */
    @IgnoreLocalizationTest
    public static final String RESOURCE_PATH_STOCK_V1 = "stocks";

    /**
     * Error code if a coupon could not be resolved
     */
    public static final String ERROR_STOCK_NOT_FOUND = "stock.not_found.error";

    private StockConstantsREST()
    {
        // restrict instantiation
    }
}
