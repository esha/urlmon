package com.esha.urlmon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceQueueProcessorTests {

    @Mock
    private ResourceMetadataProvider resourceMetadataProvider;

    @Mock
    private ResourceMetadataTopic resourceMetadataTopic;

    @InjectMocks
    private ResourceQueueProcessor resourceQueueProcessor;

    @Test
    public void processes() throws Exception {
        String resourceUrl = "http://example.com/";
        ResourceMetadata resourceMetadata = ResourceMetadata.builder().url(resourceUrl).build();
        when(this.resourceMetadataProvider.getMetadata(resourceUrl)).thenReturn(resourceMetadata);

        this.resourceQueueProcessor.process(CheckResourceCommand.builder().resourceUrl(resourceUrl).build());

        verify(this.resourceMetadataTopic).notify(ResourceCheckedEvent.builder().resourceMetadata(resourceMetadata).build());
    }
}
