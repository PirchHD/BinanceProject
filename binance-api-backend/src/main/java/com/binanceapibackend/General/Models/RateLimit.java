package com.binanceapibackend.General.Models;

import com.binanceapibackend.Market.Models.RateLimitInterval;
import com.binanceapibackend.Market.Models.RateLimitType;

public class RateLimit {

    private RateLimitType rateLimitType;

    private RateLimitInterval interval;

    private Integer limit;
}
