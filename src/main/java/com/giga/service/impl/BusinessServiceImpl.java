package com.giga.service.impl;

import com.giga.service.BusinessService;
import org.springframework.stereotype.Component;

/**
 *  BusinessServiceImpl
 *
 * @author: Aiden.gong
 * @since: 2023/1/10 11:25
 */
@Component
public class BusinessServiceImpl implements BusinessService {
    @Override
    public void doSomething() {
        System.out.println("Invoke BusinessService's method : doSomething");
    }

    @Override
    public boolean checkSomething() {
        System.out.println("Invoke BusinessService's method : checkSomething");
        return true;
    }
}
