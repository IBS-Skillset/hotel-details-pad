package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Services;
import org.opentravel.ota._2003._05.ArrayOfHotelInfoTypeService;
import org.opentravel.ota._2003._05.HotelInfoTypeService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;

@Component
public class ServicesMapper {

    public Services map(ArrayOfHotelInfoTypeService services) {
        Services.Builder serviceBuilder = Services.newBuilder();
        if (nonNull(services)) {
            List<String> serviceList = services.getService().stream()
                    .filter(Objects::nonNull)
                    .map(HotelInfoTypeService::getDescriptiveText)
                    .collect(Collectors.toList());
            safeSetProtoField(serviceBuilder::addAllService, serviceList);
        }
        return serviceBuilder.build();
    }
}
