package com.giga.statemachine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.giga.statemachine.enums.GigaEvents;
import com.giga.statemachine.enums.GigaStates;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * StateMachineInit
 *
 * @author: Aiden.gong
 * @since: 2023/1/10 11:25
 */
@Component
public class StateMachineInit {

    public static String MACHINE_ID = "TestStateMachine";


    /**
     * Init state machine instance.
     */
    @PostConstruct
    private void initStateMachine() {
        buildStateMachine(MACHINE_ID);
    }


    private StateMachine<GigaStates, GigaEvents, StateMachineContext> buildStateMachine(String machineId) {
        StateMachineBuilder<GigaStates, GigaEvents, StateMachineContext> builder = StateMachineBuilderFactory.create();
        builder.externalTransition()
                .from(GigaStates.STATE1)
                .to(GigaStates.STATE2)
                .on(GigaEvents.EVENT1)
                .when(checkCondition())
                .perform(doAction());

        builder.internalTransition()
                .within(GigaStates.STATE2)
                .on(GigaEvents.INTERNAL_EVENT)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(GigaStates.STATE2)
                .to(GigaStates.STATE1)
                .on(GigaEvents.EVENT2)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransition()
                .from(GigaStates.STATE1)
                .to(GigaStates.STATE3)
                .on(GigaEvents.EVENT3)
                .when(checkCondition())
                .perform(doAction());

        builder.externalTransitions()
                .fromAmong(GigaStates.STATE1, GigaStates.STATE2, GigaStates.STATE3)
                .to(GigaStates.STATE4)
                .on(GigaEvents.EVENT4)
                .when(checkCondition())
                .perform(doAction());

        builder.build(machineId);

        StateMachine<GigaStates, GigaEvents, StateMachineContext> stateMachine = StateMachineFactory.get(machineId);
        // Generate the state machine graph for visualization
        String plantUMLStr = stateMachine.generatePlantUML();
        System.out.println(plantUMLStr);
        return stateMachine;
    }

    private Condition<StateMachineContext> checkCondition() {
        return context -> {
            System.out.println("Check condition : " + context);
            return context.getBusinessService().checkSomething();
        };
    }

    private Action<GigaStates, GigaEvents, StateMachineContext> doAction() {
        return (from, to, event, ctx) -> {
            ctx.getBusinessService().doSomething();
            System.out.println(
                    ctx.getOperator() + " is operating " + ctx.getEntityId() + " from:" + from + " to:" + to + " on:" + event);
        };
    }

}
