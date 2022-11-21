package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.common.AvailableHotelItem;
import com.hotel.service.common.ResponseStatus;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hotel.service.description.Media;
import com.hotel.service.description.SafetyInfos;
import com.hotel.service.description.Descriptions;
import com.hotel.service.description.Services;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRSHotelDescriptiveContents;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.opentravel.ota._2003._05.HotelInfoType;
import org.opentravel.ota._2003._05.HotelInfoTypePosition;
import org.opentravel.ota._2003._05.SafetyInfoDescriptions;
import org.opentravel.ota._2003._05.HotelInfoTypeDescriptions;
import org.opentravel.ota._2003._05.ArrayOfHotelInfoTypeService;
import org.opentravel.ota._2003._05.ArrayOfContactInfoRootType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.ErrorType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
public class DescriptionResponseMapperTest {
    @Mock
    MediaMapper mediaMapper;
    @Mock
    SafetyInfosMapper safetyInfosMapper;
    @Mock
    HotelItemMapper hotelItemMapper;
    @Mock
    DescriptionsMapper descriptionsMapper;
    @Mock
    ServicesMapper servicesMapper;
    @Mock
    ErrorResponseMapper errorResponseMapper;
    @InjectMocks
    DescriptionResponseMapper descriptionResponseMapper;

    @Test
    public void map() {

        OTAHotelDescriptiveInfoRS response = getDescriptiveInfos();
        Media media = Media.newBuilder().addMediaUrl("https://picsum.photos/536/354").build();
        SafetyInfos safetyInfos = SafetyInfos.newBuilder().addSafetyInfo("fire Safety").build();
        Descriptions descriptions = Descriptions.newBuilder().addDecription("hotel Description").build();
        Services service = Services.newBuilder().addService("free Wifi").build();
        AvailableHotelItem.Builder hotelItem = AvailableHotelItem.newBuilder().setLatitude(1274.345);
        when(mediaMapper.map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getMultimediaDescriptions())).thenReturn(media);
        when(safetyInfosMapper.map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getSafetyInfo())).thenReturn(safetyInfos);
        when(descriptionsMapper.map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getDescriptions())).thenReturn(descriptions);
        when(servicesMapper.map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getServices())).thenReturn(service);
        when(hotelItemMapper.map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getContactInfos(), response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getPosition())).thenReturn(hotelItem);
        HotelDescriptionResponse hotelDescriptionResponse = descriptionResponseMapper.map(response);
        assertThat(hotelDescriptionResponse).isNotNull();
        assertThat(response.getSuccess().getValue()).isEqualTo("SUCCESS");
        assertThat(hotelDescriptionResponse.getMedia().getMediaUrl(0)).isEqualTo("https://picsum.photos/536/354");
        assertThat(hotelDescriptionResponse.getSafetyInfo().getSafetyInfo(0)).isEqualTo("fire Safety");
        assertThat(hotelDescriptionResponse.getDescriptions().getDecription(0)).isEqualTo("hotel Description");
        assertThat(hotelDescriptionResponse.getServices().getService(0)).isEqualTo("free Wifi");
        assertThat(hotelDescriptionResponse.getHotelItem().getLatitude()).isEqualTo(1274.345);
        assertThat(hotelDescriptionResponse.getHotelItem().getHotelName()).isEqualTo("ABC");
        verify(mediaMapper, atLeast(1)).map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getMultimediaDescriptions());
        verify(safetyInfosMapper, atLeast(1)).map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getSafetyInfo());
        verify(descriptionsMapper, atLeast(1)).map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getDescriptions());
        verify(servicesMapper, atLeast(1)).map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getServices());
        verify(hotelItemMapper, atLeast(1)).map(response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getContactInfos(), response.getHotelDescriptiveContents().getHotelDescriptiveContent().get(0).getHotelInfo().getPosition());
    }

    @Test
    public void testErrorCase() {

        OTAHotelDescriptiveInfoRS response = new OTAHotelDescriptiveInfoRS();
        SuccessType success = new SuccessType();
        success.setValue("FAILURE");
        response.setSuccess(success);
        ErrorsType errors = new ErrorsType();
        ErrorType error = new ErrorType();
        error.setCode("1234");
        error.setValue("room not available");
        errors.getError().add(error);
        response.setErrors(errors);
        ResponseStatus.Builder responseSatus = ResponseStatus.newBuilder();
        when(errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                response.getErrors().getError().get(0).getValue())).thenReturn(responseSatus.build());
        HotelDescriptionResponse hotelDescriptionResponse = descriptionResponseMapper.map(response);
        assertThat(hotelDescriptionResponse).isNotNull();
        verify(errorResponseMapper, atLeast(1)).mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                response.getErrors().getError().get(0).getValue());
    }

    private OTAHotelDescriptiveInfoRS getDescriptiveInfos() {
        OTAHotelDescriptiveInfoRS response = new OTAHotelDescriptiveInfoRS();
        SuccessType success = new SuccessType();
        success.setValue("SUCCESS");
        response.setSuccess(success);
        OTAHotelDescriptiveInfoRSHotelDescriptiveContents hotelDescriptiveContents = new OTAHotelDescriptiveInfoRSHotelDescriptiveContents();
        hotelDescriptiveContents.setHotelName("ABC");
        OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent hotelDescriptiveContent = new OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent();
        MultimediaDescriptionsType multimedia = new MultimediaDescriptionsType();
        hotelDescriptiveContent.setMultimediaDescriptions(multimedia);
        HotelInfoType hotelInfo = new HotelInfoType();
        SafetyInfoDescriptions safetyInfo = new SafetyInfoDescriptions();
        HotelInfoTypeDescriptions hotelInfoDescriptions = new HotelInfoTypeDescriptions();
        ArrayOfHotelInfoTypeService service = new ArrayOfHotelInfoTypeService();
        HotelInfoTypePosition position = new HotelInfoTypePosition();
        ArrayOfContactInfoRootType contactInfo = new ArrayOfContactInfoRootType();
        hotelInfo.setSafetyInfo(safetyInfo);
        hotelInfo.setDescriptions(hotelInfoDescriptions);
        hotelInfo.setServices(service);
        hotelInfo.setPosition(position);
        hotelDescriptiveContent.setHotelInfo(hotelInfo);
        hotelDescriptiveContent.setContactInfos(contactInfo);
        hotelDescriptiveContents.getHotelDescriptiveContent().add(hotelDescriptiveContent);
        response.setHotelDescriptiveContents(hotelDescriptiveContents);
        return response;
    }
}