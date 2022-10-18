package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Services;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfHotelInfoTypeService;
import org.opentravel.ota._2003._05.HotelInfoTypeService;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServicesMapperTest {
    @InjectMocks
    ServicesMapper servicesMapper;

    @Test
    public void map() {
        ArrayOfHotelInfoTypeService services = getServices();
        Services service = servicesMapper.map(services);
        assertThat(service).isNotNull();
        assertThat(service.getService(0)).isEqualTo("Free wifi");
    }

    private ArrayOfHotelInfoTypeService getServices() {
        ArrayOfHotelInfoTypeService services = new ArrayOfHotelInfoTypeService();
        HotelInfoTypeService hotelInfoTypeService = new HotelInfoTypeService();
        hotelInfoTypeService.setDescriptiveText("Free wifi");
        services.getService().add(hotelInfoTypeService);
        return services;
    }
}