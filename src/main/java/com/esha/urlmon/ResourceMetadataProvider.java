package com.esha.urlmon;

import java.io.IOException;

public interface ResourceMetadataProvider {

    ResourceMetadata getMetadata(String resourceUrl) throws IOException;
}
