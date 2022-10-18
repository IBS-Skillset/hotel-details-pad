package com.hoteldetails.pad.mappers.hoteldescription.request;

import com.hotel.service.description.HotelDescriptionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQHotelDescriptiveInfos;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DescriptiveInfoMapperTest {
    @InjectMocks
    DescriptiveInfoMapper descriptiveInfoMapper;

    @Test
    public void map() {
        HotelDescriptionRequest hotelDescriptionRequest = getDescriptionRequest();
        OTAHotelDescriptiveInfoRQHotelDescriptiveInfos response = descriptiveInfoMapper.map(hotelDescriptionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getHotelDescriptiveInfo().get(0).getHotelCode()).isEqualTo("1234");
        assertThat(response.getHotelDescriptiveInfo().get(0).getHotelCityCode()).isEqualTo("FR");
    }

    private HotelDescriptionRequest getDescriptionRequest() {
        HotelDescriptionRequest.Builder requestBuilder = HotelDescriptionRequest.newBuilder();
        requestBuilder.setHotelCode("1234").setHotelCityCode("FR");
        return requestBuilder.build();
    }
}
