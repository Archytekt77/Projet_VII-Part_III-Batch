package com.loicmaria.batch.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "api", url = "localhost:9000")
public interface ApiProxy {

    @RequestMapping(value = "bookings/expired-emails", method = RequestMethod.GET)
    List<String> getExpiredBookings();
}
