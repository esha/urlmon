package com.esha.urlmon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class DefaultResourceMetadataProvider implements ResourceMetadataProvider {

    private final URLConnectionFactory connectionFactory;

    @Value("{resource.headers.version:ETag}") // Default value reveals obvious bias for HTTP(S)
    private String versionHeaderName;

    @Autowired
    public DefaultResourceMetadataProvider(URLConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public ResourceMetadata getMetadata(String resourceUrl) throws IOException {
        URLConnection connection = this.connectionFactory.open(resourceUrl);

        String version = getVersion(connection, this.versionHeaderName).orElse("");

        long lastModified = getLastModified(connection);

        return ResourceMetadata.builder().url(resourceUrl).version(version).lastModified(lastModified).build();
    }

    private static Optional<String> getVersion(URLConnection connection, String versionHeaderName) {
        return Optional.ofNullable(connection.getHeaderField(versionHeaderName));
    }

    private static long getLastModified(URLConnection connection) {
        long lastModified = Math.min(connection.getLastModified(), connection.getDate());
        if (lastModified > 0) {
            return lastModified;
        } else {
            return new Date().getTime();
        }
    }
}
