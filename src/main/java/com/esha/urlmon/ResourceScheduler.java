package com.esha.urlmon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class ResourceScheduler {

    private static final long DAILY = 86400000;

    private final ResourceMetadataRepository resourceMetadataRepository;
    private final ResourceQueue resourceQueue;

    @Autowired
    public ResourceScheduler(ResourceMetadataRepository resourceMetadataRepository, ResourceQueue resourceQueue) {
        this.resourceMetadataRepository = requireNonNull(resourceMetadataRepository);
        this.resourceQueue = requireNonNull(resourceQueue);
    }

    @Scheduled(fixedDelay = DAILY)
    public void enqueueResources() {
        //TODO Only check URLs which haven't updated recently (e.g., last n days/weeks/months)
        for (ResourceMetadataEntity resource : this.resourceMetadataRepository.findAll()) {
            this.resourceQueue.enqueue(resource.getUrl());
        }
    }
}
