package models;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

public class Page {
    private int duration;
    private String name;
    private String url;

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page(int duration, String name, String url) {
        super();
        this.duration = duration;
        this.name = name;
        this.url = url;
    }

    public static Page fromJson(JsonNode json) {
        int jsonDuration = json.findValue("duration").asInt();
        String jsonName = json.findValue("name").asText();
        String jsonUrl = json.findValue("url").asText();
        return new Page(jsonDuration, jsonName, jsonUrl);
    }

    public boolean validate() {
        boolean validates = true;
        validates = duration > 0 && name != null && name.length() > 0 && url != null
                && url.length() > 0;
        return validates;
    }

    public JsonNode toObjectNode() {
        ObjectNode pageAsJson = Json.newObject();
        pageAsJson.put("duration", duration);
        pageAsJson.put("name", name);
        pageAsJson.put("url", url);
        return pageAsJson;
    }

    @Override
    public String toString() {
        return String.format("duration: %d name: %s url: %s", duration, name, url);
    }
}
