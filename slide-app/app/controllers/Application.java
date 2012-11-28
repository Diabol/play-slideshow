package controllers;

import models.Page;
import models.PageStorage;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    private static final PageStorage pgStorage = PageStorage.getInstance();

    public static Result index() {
        return ok(index.render());
    }

    public static Result getPages() {
        JsonFactory factory = new JsonFactory();
        ObjectMapper om = new ObjectMapper(factory);
        ArrayNode pageArray = om.createArrayNode();
        for (Page page : pgStorage.getPages()) {
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
            for (Page saved : pgStorage.getPages()) {
                if (saved.getName().equals(page.getName())) {
                    found = true;
                }
            }
            if (!found) {
                pgStorage.addPage(page);
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

    public static Result removePage(String name) {
        pgStorage.removePage(name);
        return ok();
    }
}