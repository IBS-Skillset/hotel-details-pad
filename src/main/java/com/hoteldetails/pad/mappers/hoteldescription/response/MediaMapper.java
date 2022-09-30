package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.description.Media;
import org.opentravel.ota._2003._05.ImageDescriptionType;
import org.opentravel.ota._2003._05.MultimediaDescriptionType;
import org.opentravel.ota._2003._05.MultimediaDescriptionsType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class MediaMapper {
    public Media map(MultimediaDescriptionsType mediaDescriptions) {
        Media.Builder media = Media.newBuilder();
        if (nonNull(mediaDescriptions) && !isEmpty(mediaDescriptions.getMultimediaDescription())) {
            List<MultimediaDescriptionType> descriptionTypes = mediaDescriptions.getMultimediaDescription();
            List<String> mediaUrlList = descriptionTypes.get(0).getImageItems().getImageItem().stream()
                    .filter(Objects::nonNull)
                    .map(ImageDescriptionType::getDescription)
                    .collect(Collectors.toList());
            safeSetProtoField(media::addAllMediaUrl, mediaUrlList);
        }
        return media.build();
    }

}
