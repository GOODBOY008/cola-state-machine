package com.giga.statemachine;

import com.giga.service.BusinessService;

/**
 * Context for StateMachine.
 */

public class StateMachineContext {
    private String operator;
    private String entityId;

    private BusinessService businessService;

    public StateMachineContext(String operator, String entityId, BusinessService businessService) {
        this.operator = operator;
        this.entityId = entityId;
        this.businessService = businessService;
    }

    public String getOperator() {
        return operator;
    }

    public String getEntityId() {
        return entityId;
    }

    public BusinessService getBusinessService() {
        return businessService;
    }
}
