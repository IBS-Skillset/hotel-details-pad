package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Descriptions;
import org.opentravel.ota._2003._05.HotelInfoTypeDescriptions;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class DescriptionsMapper {

    public Descriptions map(HotelInfoTypeDescriptions descriptions) {
        Descriptions.Builder descriptionsBuilder = Descriptions.newBuilder();
        if (nonNull(descriptions)) {
            safeSetProtoField(descriptionsBuilder::addAllDecription, descriptions.getDescriptiveText());
        }
        return descriptionsBuilder.build();
    }
}
