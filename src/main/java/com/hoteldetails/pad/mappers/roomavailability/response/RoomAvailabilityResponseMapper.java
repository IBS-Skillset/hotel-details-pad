package com.hoteldetails.pad.mappers.roomavailability.response;


import com.hotel.service.common.ResponseStatus;
import com.hotel.service.roomavailability.Rate;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;

import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static com.hoteldetails.pad.util.ApiConstants.SUCCESS;
import static java.util.Objects.nonNull;

@Component
public class RoomAvailabilityResponseMapper {

    @Autowired
    RateMapper rateMapper;

    @Autowired
    ErrorResponseMapper errorResponseMapper;

    public RoomAvailabilityResponse map(PropertyAvailabilityRS response) {
        RoomAvailabilityResponse.Builder responseBuilder = RoomAvailabilityResponse.newBuilder();
        if (nonNull(response.getSuccess().getValue()) && nonNull(response.getHotelRates())) {
            ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(responseStatusBuilder::setStatus, SUCCESS);
            safeSetProtoField(responseBuilder::setResponseStatus, responseStatusBuilder);
            safeSetProtoField(responseBuilder::setHotelCode, response.getHotelRates().getHotel().get(0).getID());
            List<Rate> rateList = response.getHotelRates().getHotel().get(0)
                    .getBookingChannel().get(0).getRatePlan().stream().map(rateMapper::map)
                    .collect(Collectors.toList());
            safeSetProtoField(responseBuilder::addAllRateList, rateList);
        } else {
            safeSetProtoField(responseBuilder::setResponseStatus,
                    errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                            response.getErrors().getError().get(0).getValue()));
        }
        return responseBuilder.build();
    }
}



