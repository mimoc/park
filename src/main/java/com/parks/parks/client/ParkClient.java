package com.parks.parks.client;

import java.util.List;
import java.util.Map;

public interface ParkClient {
    List<Map> callExternalURI(Map multiValueMap);
}
