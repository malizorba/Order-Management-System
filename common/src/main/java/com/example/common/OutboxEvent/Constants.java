package com.example.common.OutboxEvent;

import jdk.jfr.EventFactory;

public class Constants {

    // Group 1: Aggregate Types
    public enum AggregateType {
        ORDER("ORDER"),
        INVENTORY("INVENTORY"),
        PAYMENT("PAYMENT"),
        PRODUCT("PRODUCT");


        private final String value;

        AggregateType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Group 2: Event Types
    public enum EventType {
        ORDER_CREATED("ORDER_CREATED"),
        PAYMENT_PROCESSED("PAYMENT_PROCESSED"),
        STOCK_AVAILABLE("STOCK_AVAILABLE"),
        PRODUCT_ADDED("PRODUCT_ADDED"),
        ORDER_FAILED("ORDER_FAILED"),
        ORDER_VALIDATED("ORDER_VALIDATED"),
        PAYMENT_FAILED("PAYMENT_FAILED"),
        ORDER_PAID("ORDER_PAID");


        private final String value;

        EventType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}
