package com.esha.urlmon;

import java.io.IOException;
import java.net.URLConnection;

public interface URLConnectionFactory {

    URLConnection open(String url) throws IOException;
}
