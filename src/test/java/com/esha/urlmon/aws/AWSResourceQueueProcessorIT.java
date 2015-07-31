package com.esha.urlmon.aws;

import com.esha.urlmon.Application;
import com.esha.urlmon.CheckResourceCommand;
import com.esha.urlmon.ResourceCheckedEvent;
import com.esha.urlmon.ResourceQueueProcessor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AWSResourceQueueProcessorIT {

    @Autowired
    private MessageListener messageListener;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourceQueueProcessor resourceQueueProcessor;

    @Test
    public void processes() throws Exception {
        this.messageListener.setCountDownLatch(new CountDownLatch(1));
        this.messageListener.getReceivedMessages().clear();

        String resourceUrl = "http://www.esha.com/";
        this.resourceQueueProcessor.process(CheckResourceCommand.builder().resourceUrl(resourceUrl).build());

        assertTrue(this.messageListener.getCountDownLatch().await(15, TimeUnit.SECONDS));
        assertThat(this.messageListener.getReceivedMessages(), is(not(empty())));

        Message message = this.objectMapper.readValue(this.messageListener.getReceivedMessages().get(0), Message.class);
        assertThat(message.type, is("Notification"));
        assertThat(message.subject, is(SNSResourceMetadataTopic.DEFAULT_SNS_SUBJECT));

        ResourceCheckedEvent event = this.objectMapper.readValue(message.body, ResourceCheckedEvent.class);
        assertThat(event.getResourceMetadata().getUrl(), is(equalTo(resourceUrl)));
    }

    public static class MessageListener {

        private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

        private final List<String> receivedMessages = new ArrayList<>();

        private CountDownLatch countDownLatch = new CountDownLatch(1);

        @MessageMapping("ResourceMetadataTopicQueue")
        public void receivesMessage(String message) throws Exception {
            logger.debug("Received message with content {}", message);
            this.receivedMessages.add(message);
            this.countDownLatch.countDown();
        }

        public List<String> getReceivedMessages() {
            return this.receivedMessages;
        }

        public CountDownLatch getCountDownLatch() {
            return this.countDownLatch;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }
    }

    private static class Message {
        @JsonProperty("Type")
        public String type;

        @JsonProperty("Subject")
        public String subject;

        @JsonProperty("Message")
        public String body;
    }
}
