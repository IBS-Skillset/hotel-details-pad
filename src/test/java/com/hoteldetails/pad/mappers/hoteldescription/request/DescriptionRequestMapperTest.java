package com.hoteldetails.pad.mappers.hoteldescription.request;

import com.hotel.service.common.Context;
import com.hotel.service.description.HotelDescriptionRequest;
import com.hoteldetails.pad.mappers.common.request.PosMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQHotelDescriptiveInfos;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
public class DescriptionRequestMapperTest {
    @Mock
    PosMapper posMapper;
    @Mock
    DescriptiveInfoMapper descriptiveInfoMapper;
    @InjectMocks
    DescriptionRequestMapper descriptionRequestMapper;

    @Test
    public void map() {
        HotelDescriptionRequest hotelDescriptionRequest = getDescriptionRequest();
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requestorID = new SourceType.RequestorID();
        requestorID.setID("1234");
        requestorID.setLanguageCode("FRE");
        sourceType.setRequestorID(requestorID);
        arrayOfSourceType.getSource().add(sourceType);
        OTAHotelDescriptiveInfoRQHotelDescriptiveInfos hotelDescriptiveInfos = new OTAHotelDescriptiveInfoRQHotelDescriptiveInfos();
        OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo hotelDescriptiveInfo = new OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo();
        hotelDescriptiveInfo.setHotelCode(hotelDescriptionRequest.getHotelCode());
        hotelDescriptiveInfos.getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
        when(posMapper.mapPOS(hotelDescriptionRequest.getRequestContext(), hotelDescriptionRequest.getLanguageCode())).thenReturn(arrayOfSourceType);
        when(descriptiveInfoMapper.map(hotelDescriptionRequest)).thenReturn(hotelDescriptiveInfos);
        OTAHotelDescriptiveInfoRQ response = descriptionRequestMapper.map(hotelDescriptionRequest);
        assertThat(response).isNotNull();
        assertThat(response.getPOS().getSource().get(0).getRequestorID().getID()).isEqualTo("1234");
        assertThat(response.getPOS().getSource().get(0).getRequestorID().getLanguageCode()).isEqualTo("FRE");
        assertThat(response.getHotelDescriptiveInfos().getHotelDescriptiveInfo().get(0).getHotelCode()).isEqualTo("Hotel123");
        verify(posMapper, atLeast(1)).mapPOS(hotelDescriptionRequest.getRequestContext(), hotelDescriptionRequest.getLanguageCode());
        verify(descriptiveInfoMapper, atLeast(1)).map(hotelDescriptionRequest);
    }

    private HotelDescriptionRequest getDescriptionRequest() {
        HotelDescriptionRequest request = HotelDescriptionRequest.newBuilder().
                setRequestContext(Context.newBuilder().build())
                .setLanguageCode("FRE")
                .setHotelCode("Hotel123")
                .build();
        return request;
    }
}