package com.loicmaria.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.loicmaria.batch.proxies.ApiProxy;

import java.util.List;

@Component
public class ApiReader implements ItemReader<List<String>> {

    private final ApiProxy apiProxy;

    @Autowired
    public ApiReader(ApiProxy apiProxy) {
        this.apiProxy = apiProxy;
    }

    @Override
    public List<String> read() {
        return apiProxy.getExpiredBookings();
    }
}