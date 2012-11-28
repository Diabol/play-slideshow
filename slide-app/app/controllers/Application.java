package controllers;

import java.util.List;

import models.Page;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result getPages() {
        List<Page> pages = Page.find.all();
        JsonFactory factory = new JsonFactory();
        ObjectMapper om = new ObjectMapper(factory);
        ArrayNode pageArray = om.createArrayNode();
        for (Page page : pages) {
            ObjectNode pageAsJson = Json.newObject();
            pageAsJson.put("name", page.name);
            pageAsJson.put("duration", page.duration);
            pageAsJson.put("url", page.url);
            pageArray.add(pageAsJson);
        }
        return ok(pageArray);
    }

    public static Result addPage() {
        RequestBody body = request().body();
        JsonNode json = body.asJson();
        Page page = Page.fromJson(json);
        if (page.validate()) {
            List<Page> pages = Page.find.where().eq("name", page.name).findList();
            if (pages.size() == 0) {
                page.save();
                return ok();
            } else {
                return badRequest("Page with name '" + page.name
                        + "' already added! Remove with POST to http://host:port/pages/"
                        + page.name + "/remove");
            }
        } else {
            return badRequest("Can't create Page from " + json.asText());
        }
    }

    public static synchronized Result removePage(String name) {
        List<Page> foundPages = Page.find.where().eq("name", name).findList();
        for (Page found : foundPages) {
            found.delete();
        }
        return ok();
    }
}