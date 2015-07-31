package com.esha.urlmon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Value;
import lombok.experimental.Builder;

@Builder
@Value
@JsonDeserialize(builder = CheckResourceCommand.CheckResourceCommandBuilder.class)
public class CheckResourceCommand {

    public final String resourceUrl;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class CheckResourceCommandBuilder {}
}
