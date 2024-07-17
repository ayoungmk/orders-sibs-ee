package eu.sibs.ordersibs.model.enums;

public enum StatusOrder {

    INCOMPLETE("Incomplete"),
    COMPLETE("Complete");


    private final String value;

    StatusOrder(final String value) {
        this.value = value;
    }

}
