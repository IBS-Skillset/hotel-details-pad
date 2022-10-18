package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Media;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ImageItemsType;
import org.opentravel.ota._2003._05.MultimediaDescriptionType;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MediaMapperTest {
    @InjectMocks
    MediaMapper mediaMapper;

    @Test
    public void map() {
        MultimediaDescriptionsType mediaDescriptions = getMediaDescription();
        Media media = mediaMapper.map(mediaDescriptions);
        assertThat(media).isNotNull();
        assertThat(media.getMediaUrl(0)).isEqualTo("https://picsum.photos/");
    }

    private MultimediaDescriptionsType getMediaDescription() {
        MultimediaDescriptionsType mediaDescriptions = new MultimediaDescriptionsType();
        MultimediaDescriptionType descriptionType = new MultimediaDescriptionType();
        ImageItemsType imageItemsType = new ImageItemsType();
        ImageItemsType.ImageItem imageItem = new ImageItemsType.ImageItem();
        imageItem.setDescription("https://picsum.photos/");
        imageItemsType.getImageItem().add(imageItem);
        descriptionType.setImageItems(imageItemsType);
        mediaDescriptions.getMultimediaDescription().add(descriptionType);
        return mediaDescriptions;
    }
}

