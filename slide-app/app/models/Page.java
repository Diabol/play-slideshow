package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class Page extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long pageId;
    @Constraints.Required
    public int duration;
    @Constraints.Required
    public String name;
    @Constraints.Required
    public String url;

    @OneToOne
    @JoinColumn(name = "vc_id", nullable = false)
    public Page vcInfo;

    public static Finder<Long, Page> find = new Finder<Long, Page>(Long.class, Page.class);

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
