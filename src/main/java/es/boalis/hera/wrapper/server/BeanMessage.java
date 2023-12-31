package es.boalis.hera.wrapper.server;

import java.util.Map;

public class BeanMessage {
    private String metric;
    private String value;
    private long timestamp;

    private Map<String,String> tags;

    public BeanMessage(String metric, String value, long timestamp) {
        this.metric = metric;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public String getMetric() {
        return metric;
    }

    public String getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BeanMessage{" +
                "metric='" + metric + '\'' +
                ", value='" + value + '\'' +
                ", timestamp=" + timestamp +
                ", tags=" + tags +
                '}';
    }
}
