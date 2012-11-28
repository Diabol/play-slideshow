package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Page;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    private static final List<Page> pages = new ArrayList<Page>();

    public static Result index() {
        return ok(index.render());
    }

    public static Result getPages() {
        JsonFactory factory = new JsonFactory();
        ObjectMapper om = new ObjectMapper(factory);
        ArrayNode pageArray = om.createArrayNode();
        for (Page page : pages) {
            pageArray.add(page.toObjectNode());
        }
        return ok(pageArray);
    }

    public static Result addPage() {
        RequestBody body = request().body();
        JsonNode json = body.asJson();
        Page page = Page.fromJson(json);
        if (page.validate()) {
            boolean found = false;
            for (Page saved : pages) {
                if (saved.getName().equals(page.getName())) {
                    found = true;
                }
            }
            if (found) {
                pages.add(page);
                return ok();
            } else {
                return badRequest("Page with name '" + page.getName()
                        + "' already added! Remove with POST to http://host:port/pages/"
                        + page.getName() + "/remove");
            }
        } else {
            return badRequest("Can't create Page from " + json.asText());
        }
    }

    public static synchronized Result removePage(String name) {
        for (Page saved : pages) {
            if (saved.getName().equals(name)) {
                pages.remove(saved);
            }
        }
        return ok();
    }
}