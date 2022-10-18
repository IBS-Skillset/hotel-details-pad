package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.SafetyInfos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.SafetyInfoDescriptions;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SafetyInfosMapperTest {
    @InjectMocks
    SafetyInfosMapper safetyInfosMapper;

    @Test
    public void map() {
        SafetyInfoDescriptions safetyInfos = getSaftyINfos();
        SafetyInfos response = safetyInfosMapper.map(safetyInfos);
        assertThat(response).isNotNull();
        assertThat(response.getSafetyInfo(0)).isEqualTo("room12B");
    }

    private SafetyInfoDescriptions getSaftyINfos() {
        SafetyInfoDescriptions safetyInfos = new SafetyInfoDescriptions();
        safetyInfos.getHotelRandomData().add("room12B");
        return safetyInfos;
    }
}