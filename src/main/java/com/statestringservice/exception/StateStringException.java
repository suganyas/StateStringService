package com.statestringservice.exception;

public class StateStringException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        private final String type;

        public StateStringException(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
}
