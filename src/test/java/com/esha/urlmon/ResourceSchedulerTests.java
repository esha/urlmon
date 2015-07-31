package com.esha.urlmon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceSchedulerTests {

    @Mock
    private ResourceMetadataRepository resourceMetadataRepository;

    @Mock
    private ResourceQueue resourceQueue;

    @InjectMocks
    private ResourceScheduler resourceScheduler;

    @Test
    public void enqueuesResources() {
        ResourceMetadataEntity entity = new ResourceMetadataEntity(1, "http://example.com/", "1", new Date().getTime());
        when(this.resourceMetadataRepository.findAll()).thenReturn(Collections.singletonList(entity));

        this.resourceScheduler.enqueueResources();

        verify(this.resourceQueue).enqueue(entity.getUrl());
    }
}
