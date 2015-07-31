package com.esha.urlmon;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class DefaultURLConnectionFactory implements URLConnectionFactory {

    @Override
    public URLConnection open(String url) throws IOException {
        return new URL(url).openConnection();
    }
}
