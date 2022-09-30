package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.common.Address;
import com.hotel.service.common.AvailableHotelItem;
import org.opentravel.ota._2003._05.AddressesTypeAddress;
import org.opentravel.ota._2003._05.ArrayOfContactInfoRootType;
import org.opentravel.ota._2003._05.ContactInfoRootType;
import org.opentravel.ota._2003._05.HotelInfoTypePosition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static com.hoteldetails.pad.util.ApiConstants.SEPERATOR;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class HotelItemMapper {

    public AvailableHotelItem map(ArrayOfContactInfoRootType contactInfo, HotelInfoTypePosition position) {
        AvailableHotelItem.Builder hotelItem = AvailableHotelItem.newBuilder();
        if (nonNull(position)) {
            safeSetProtoField(hotelItem::setLongitude, Double.parseDouble(position.getLongitude()));
            safeSetProtoField(hotelItem::setLatitude, Double.parseDouble(position.getLatitude()));
        }
        if (nonNull(contactInfo) && !isEmpty(contactInfo.getContactInfo())) {
            Address.Builder addressBuilder = Address.newBuilder();
            List<ContactInfoRootType> contactInfos = contactInfo.getContactInfo();
            if (nonNull(contactInfos.get(0).getAddresses()) && !isEmpty(contactInfos.get(0).getAddresses().getAddress())) {
                AddressesTypeAddress address = contactInfo.getContactInfo().get(0).getAddresses().getAddress().get(0);
                safeSetProtoField(addressBuilder::setStreetAddress, address.getAddressLine().stream()
                        .map(Objects::toString)
                        .collect(Collectors.joining(SEPERATOR)));
                safeSetProtoField(addressBuilder::setZipCode, address.getPostalCode());
                safeSetProtoField(addressBuilder::setCityName, address.getCityName());
                safeSetProtoField(addressBuilder::setCountryCode, address.getCountryName().getCode());
                safeSetProtoField(addressBuilder::setCountryName, address.getCountryName().getValue());

            }
            if (nonNull(contactInfos.get(0).getPhones()) && !isEmpty(contactInfos.get(0).getPhones().getPhone())) {
                safeSetProtoField(addressBuilder::setPhoneNumber, contactInfos.get(0).getPhones().getPhone().get(0).getPhoneNumber());
            }
            safeSetProtoField(hotelItem::setAddress, addressBuilder);
        }

        return hotelItem.build();
    }
}
