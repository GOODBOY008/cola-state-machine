package com.giga;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.giga.service.BusinessService;
import com.giga.statemachine.StateMachineContext;
import com.giga.statemachine.StateMachineInit;
import com.giga.statemachine.enums.GigaEvents;
import com.giga.statemachine.enums.GigaStates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.UUID;

@SpringBootTest
class ColaStateMachineApplicationTests {

    @Resource
    private BusinessService businessService;

    @Test
    void testStateMachine() {
        // get state machine instance
        StateMachine<GigaStates,GigaEvents,StateMachineContext> stateMachine =  StateMachineFactory.get(StateMachineInit.MACHINE_ID);

        // fire event test
        GigaStates target = stateMachine.fireEvent(GigaStates.STATE1, GigaEvents.EVENT1, new StateMachineContext("Giga", UUID.randomUUID().toString(), businessService));
        Assertions.assertEquals(GigaStates.STATE2, target);
        target = stateMachine.fireEvent(GigaStates.STATE2, GigaEvents.INTERNAL_EVENT, new StateMachineContext("Giga", UUID.randomUUID().toString(), businessService));
        Assertions.assertEquals(GigaStates.STATE2, target);
        target = stateMachine.fireEvent(GigaStates.STATE2, GigaEvents.EVENT2, new StateMachineContext("Giga", UUID.randomUUID().toString(), businessService));
        Assertions.assertEquals(GigaStates.STATE1, target);
        target = stateMachine.fireEvent(GigaStates.STATE1, GigaEvents.EVENT3, new StateMachineContext("Giga", UUID.randomUUID().toString(), businessService));
        Assertions.assertEquals(GigaStates.STATE3, target);

        //---- Print plantUML ----//
        System.out.println(stateMachine.generatePlantUML());
        //-----------------------//
    }


}
