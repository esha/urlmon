package com.esha.urlmon.aws;

import com.esha.urlmon.ResourceCheckedEvent;
import com.esha.urlmon.ResourceMetadataTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Component
public class SNSResourceMetadataTopic implements ResourceMetadataTopic {

    static final String DEFAULT_SNS_SUBJECT = "ResourceCheckedEvent";

    private final NotificationMessagingTemplate notificationMessagingTemplate;
    private final ObjectMapper objectMapper;

    @Value("${sns.topicName:ResourceMetadataTopic}")
    private String snsTopicName;

    @Autowired
    public SNSResourceMetadataTopic(NotificationMessagingTemplate notificationMessagingTemplate, ObjectMapper objectMapper) {
        this.notificationMessagingTemplate = requireNonNull(notificationMessagingTemplate);
        this.objectMapper = requireNonNull(objectMapper);
    }

    @Override
    public void notify(ResourceCheckedEvent event) throws Exception {
        //FIXME Instead of propagating any exception, notify the dead-letter topic
        String message = this.objectMapper.writeValueAsString(event);

        this.notificationMessagingTemplate.sendNotification(this.snsTopicName, message, DEFAULT_SNS_SUBJECT);
    }
}
