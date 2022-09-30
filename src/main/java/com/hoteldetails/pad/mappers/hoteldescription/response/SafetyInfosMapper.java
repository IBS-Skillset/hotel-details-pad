package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.SafetyInfos;
import org.opentravel.ota._2003._05.SafetyInfoDescriptions;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class SafetyInfosMapper {

    public SafetyInfos map(SafetyInfoDescriptions safetyInfo) {
        SafetyInfos.Builder safetyInfosBuilder = SafetyInfos.newBuilder();
        if (nonNull(safetyInfo)) {
            safeSetProtoField(safetyInfosBuilder::addAllSafetyInfo, safetyInfo.getHotelRandomData());
        }
        return safetyInfosBuilder.build();
    }
}
