package com.esha.urlmon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Component
public class ResourceQueueProcessor {

    public static final String QUEUE_NAME = "ResourceQueue";

    private final ResourceMetadataProvider resourceMetadataProvider;
    private final ResourceMetadataTopic resourceMetadataTopic;

    @Autowired
    public ResourceQueueProcessor(ResourceMetadataProvider resourceMetadataProvider, ResourceMetadataTopic resourceMetadataTopic) {
        this.resourceMetadataProvider = requireNonNull(resourceMetadataProvider);
        this.resourceMetadataTopic = requireNonNull(resourceMetadataTopic);
    }

    @MessageMapping(QUEUE_NAME)
    public void process(CheckResourceCommand command) throws Exception {
        ResourceMetadata resourceMetadata = this.resourceMetadataProvider.getMetadata(command.resourceUrl);

        this.resourceMetadataTopic.notify(ResourceCheckedEvent.builder().resourceMetadata(resourceMetadata).build());
    }
}
