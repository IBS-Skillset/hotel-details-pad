package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Descriptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.HotelInfoTypeDescriptions;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DescriptionsMapperTest {
    @InjectMocks
    DescriptionsMapper descriptionsMapper;

    @Test
    public void map() {
        HotelInfoTypeDescriptions hotelInfoTypeDescriptions = getDescription();
        Descriptions descriptions = descriptionsMapper.map(hotelInfoTypeDescriptions);
        assertThat(descriptions).isNotNull();
        assertThat(descriptions.getDecription(0)).isEqualTo("Hotel Paris");
    }

    private HotelInfoTypeDescriptions getDescription() {
        HotelInfoTypeDescriptions hotelInfoTypeDescriptions = new HotelInfoTypeDescriptions();
        hotelInfoTypeDescriptions.getDescriptiveText().add("Hotel Paris");
        return hotelInfoTypeDescriptions;
    }
}