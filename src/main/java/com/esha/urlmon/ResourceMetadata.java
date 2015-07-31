package com.esha.urlmon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Value;
import lombok.experimental.Builder;

@Builder
@Value
@JsonDeserialize(builder = ResourceMetadata.ResourceMetadataBuilder.class)
public class ResourceMetadata {

    private final String url, version;
    private final long lastModified;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ResourceMetadataBuilder {}
}
