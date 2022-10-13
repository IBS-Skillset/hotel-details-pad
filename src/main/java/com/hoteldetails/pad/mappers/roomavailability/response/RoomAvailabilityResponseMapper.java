package com.hoteldetails.pad.mappers.roomavailability.response;


import com.hotel.service.common.ResponseStatus;
import com.hotel.service.roomavailability.Rate;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;

import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static com.hoteldetails.pad.util.ApiConstants.FAILURE;
import static com.hoteldetails.pad.util.ApiConstants.SUCCESS;
import static java.util.Objects.nonNull;

@Component
public class RoomAvailabilityResponseMapper {

    @Autowired
    RateMapper rateMapper;

    public RoomAvailabilityResponse map(PropertyAvailabilityRS response) {
        RoomAvailabilityResponse.Builder responseBuilder = RoomAvailabilityResponse.newBuilder();
        if (nonNull(response.getSuccess().getValue())) {
            ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(responseStatusBuilder::setStatus, SUCCESS);
            safeSetProtoField(responseBuilder::setResponseStatus, responseStatusBuilder);
            safeSetProtoField(responseBuilder::setHotelCode, response.getHotelRates().getHotel().get(0).getID());
            List<Rate> rateList = response.getHotelRates().getHotel().get(0)
                    .getBookingChannel().get(0).getRatePlan().stream().map(rateMapper::map)
                    .collect(Collectors.toList());
            safeSetProtoField(responseBuilder::addAllRateList, rateList);
        } else {
            ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(responseStatusBuilder::setStatus, FAILURE);
            safeSetProtoField(responseStatusBuilder::setErrorCode, response.getErrors().getError().get(0).getCode());
            safeSetProtoField(responseStatusBuilder::setErrorMessage, response.getErrors().getError().get(0).getValue());
        }
        return responseBuilder.build();
    }
}



