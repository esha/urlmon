package com.esha.urlmon.aws;

import com.esha.urlmon.ResourceQueue;
import com.esha.urlmon.ResourceQueueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SQSResourceQueue implements ResourceQueue {

    private static final String QUEUE_NAME = ResourceQueueProcessor.QUEUE_NAME;

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SQSResourceQueue(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = Objects.requireNonNull(queueMessagingTemplate);
    }

    @Override
    public void enqueue(String resourceUrl) {
        this.queueMessagingTemplate.convertAndSend(QUEUE_NAME, resourceUrl);
        this.queueMessagingTemplate.convertAndSend(QUEUE_NAME, resourceUrl);
    }
}
