package com.intershop.sellside.rest.stock.v1.capi.handler;

import com.intershop.sellside.rest.common.v1.capi.handler.FeedbackHandler;

import jakarta.ws.rs.core.Response;

public interface StockFeedbackHandler extends FeedbackHandler
{
    Response getStockNotFoundResponse();

}
