package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.common.AvailableHotelItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfContactInfoRootType;
import org.opentravel.ota._2003._05.HotelInfoTypePosition;
import org.opentravel.ota._2003._05.ContactInfoRootType;
import org.opentravel.ota._2003._05.ArrayOfAddressesTypeAddress;
import org.opentravel.ota._2003._05.AddressesTypeAddress;
import org.opentravel.ota._2003._05.CountryNameType;
import org.opentravel.ota._2003._05.ArrayOfPhonesTypePhone;
import org.opentravel.ota._2003._05.PhonesTypePhone;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class HotelItemMapperTest {
    @InjectMocks
    HotelItemMapper hotelItemMapper;

    @Test
    public void map() {
        ArrayOfContactInfoRootType contactInfo = getContactInfo();
        HotelInfoTypePosition position = getHotelInfoPosition();
        AvailableHotelItem.Builder response = hotelItemMapper.map(contactInfo, position);
        assertThat(response).isNotNull();
        assertThat(response.getLongitude()).isEqualTo(1234.0);
        assertThat(response.getLatitude()).isEqualTo(1234.0);
        assertThat(response.getAddress().getStreetAddress()).isEqualTo("new york");
        assertThat(response.getAddress().getPhoneNumber()).isEqualTo("9887655443");
        assertThat(response.getAddress().getCityName()).isEqualTo("washington DC");
        assertThat(response.getAddress().getCountryName()).isEqualTo("USA");
        assertThat(response.getAddress().getCountryCode()).isEqualTo("234");
    }

    private ArrayOfContactInfoRootType getContactInfo() {
        ArrayOfContactInfoRootType contactInfo = new ArrayOfContactInfoRootType();
        ContactInfoRootType contactInfoRootType = new ContactInfoRootType();
        ArrayOfAddressesTypeAddress arrayAddress = new ArrayOfAddressesTypeAddress();
        AddressesTypeAddress address = new AddressesTypeAddress();
        address.getAddressLine().add("new york");
        address.setPostalCode("1234");
        address.setCityName("washington DC");
        CountryNameType countryNameType = new CountryNameType();
        countryNameType.setCode("234");
        countryNameType.setValue("USA");
        address.setCountryName(countryNameType);
        arrayAddress.getAddress().add(address);
        contactInfoRootType.setAddresses(arrayAddress);
        ArrayOfPhonesTypePhone phones = new ArrayOfPhonesTypePhone();
        PhonesTypePhone phone = new PhonesTypePhone();
        phone.setPhoneNumber("9887655443");
        phones.getPhone().add(phone);
        contactInfoRootType.setPhones(phones);
        contactInfo.getContactInfo().add(contactInfoRootType);
        return contactInfo;
    }

    private HotelInfoTypePosition getHotelInfoPosition() {
        HotelInfoTypePosition position = new HotelInfoTypePosition();
        position.setLongitude("1234.0");
        position.setLatitude("1234.0");
        return position;
    }
}