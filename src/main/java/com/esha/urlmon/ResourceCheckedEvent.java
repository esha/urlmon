package com.esha.urlmon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Value;
import lombok.experimental.Builder;

@Builder
@Value
@JsonDeserialize(builder = ResourceCheckedEvent.ResourceCheckedEventBuilder.class)
public class ResourceCheckedEvent {

    public final ResourceMetadata resourceMetadata;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ResourceCheckedEventBuilder {}
}
