package com.hoteldetails.pad.mappers.roomavailability.response;

import com.hotel.service.roomavailability.BreakFast;
import com.hotel.service.roomavailability.Rate;
import com.hotel.service.roomavailability.RoomDescription;
import com.hoteldetails.pad.util.RatePlanType;
import org.opentravel.ota._2003._05.ArrayOfPropertyAvailabilityRSHotelRatesHotel;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;


@Component
public class RateMapper {

    public Rate map(ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan ratePlan) {
        Rate.Builder rateBuilder = Rate.newBuilder();
        if (nonNull(ratePlan)) {
            safeSetProtoField(rateBuilder::setAmount, Double.parseDouble(ratePlan.getRatePerDay().getAmount()));
            safeSetProtoField(rateBuilder::setTotalAmount, Double.parseDouble(ratePlan.getTotalAmount().getAmount()));
            safeSetProtoField(rateBuilder::setCurrency, ratePlan.getRatePerDay().getCurrencyCode());
            safeSetProtoField(rateBuilder::setAvailable, ratePlan.getRoomsAvailable());
            safeSetProtoField(rateBuilder::setRateType, ratePlan.getRateTypeCode());
            safeSetProtoField(rateBuilder::setIsCVVRequired, Boolean.FALSE);
            safeSetProtoField(rateBuilder::setRatePlan, ratePlan.getID());
            safeSetProtoField(rateBuilder::setBookingCode, ratePlan.getID());
            safeSetProtoField(rateBuilder::setRateCategory, ratePlan.getRateDescription());
            safeSetProtoField(rateBuilder::setRateType, RatePlanType.getRatePlan(ratePlan.getRatePlanType()));
            BreakFast.Builder builder = BreakFast.newBuilder();
            safeSetProtoField(builder::setBreakfast, ratePlan.getRateDetails().getBreakfast());
            safeSetProtoField(rateBuilder::addBreakFastDetails, builder.build());
            RoomDescription.Builder roomDescription = RoomDescription.newBuilder();
            safeSetProtoField(roomDescription::setDescription, ratePlan.getRoomRateDescription());
            safeSetProtoField(rateBuilder::addRoomDescriptionList, roomDescription.build());
            safeSetProtoField(rateBuilder::setIsBreakfastIncluded, ratePlan.getTPAExtensions().getBreakfastIncluded());

        }
        return rateBuilder.build();
    }
}


