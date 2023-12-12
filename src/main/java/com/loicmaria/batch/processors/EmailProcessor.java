package com.loicmaria.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailProcessor implements ItemProcessor<List<String>, List<String>> {

    @Override
    public List<String> process(List<String> emails){
        return emails;
    }
}
