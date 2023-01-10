package com.giga.controller;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.giga.service.BusinessService;
import com.giga.statemachine.StateMachineContext;
import com.giga.statemachine.StateMachineInit;
import com.giga.statemachine.enums.GigaEvents;
import com.giga.statemachine.enums.GigaStates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * TestController
 *
 * @author: Aiden.gong
 * @since: 2023/1/10 11:20
 */
@RestController
public class TestController {


    private final BusinessService businessService;

    public TestController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/test/ddd")
    public String testDDD() {
        StateMachine<GigaStates,GigaEvents,StateMachineContext> stateMachine =  StateMachineFactory.get(StateMachineInit.MACHINE_ID);
        GigaStates target = stateMachine.fireEvent(GigaStates.STATE1, GigaEvents.EVENT1, new StateMachineContext("Giga", UUID.randomUUID().toString(), businessService));
        return target.name();
    }
}
