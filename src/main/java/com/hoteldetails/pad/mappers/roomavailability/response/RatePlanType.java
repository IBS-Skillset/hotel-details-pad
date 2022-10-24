package com.hoteldetails.pad.mappers.roomavailability.response;

import org.apache.commons.lang.StringUtils;

import static java.util.Objects.nonNull;

public enum RatePlanType {

    AGENCY_NEGOTIATED_FARE(10, "A"),
    COMPANY_NEGOTIATED_FARE(4, "B"),
    PUBLIC_FARE(0, "P");

    private final Integer key;
    private final String value;


    RatePlanType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getRatePlan(Integer ratePlan) {
        if (nonNull(ratePlan)) {
            for (RatePlanType ratePlanType : values()) {
                if (ratePlanType.key.equals(ratePlan)) {
                    return ratePlanType.value;
                }
            }
        }
        return StringUtils.EMPTY;
    }


}

