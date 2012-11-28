import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.util.HashMap;

import junit.framework.Assert;
import models.Page;

import org.junit.Test;
import org.mockito.Mockito;

import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import controllers.Application;

public class ApplicationTest {
    private final Page page = new Page(15, "name", "url");

    @Test
    public void testAddPage() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                addPage(page);
                Result pages = callAction(controllers.routes.ref.Application.getPages());
                Assert.assertTrue(contentAsString(pages).contains(page.toObjectNode().asText()));
            }

        });
    }

    @Test
    public void testAddPageDuplicateNameReturnsBadRequest() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                addPage(page);
                Result badRequest = addPage(new Page(15, page.name, "someurl"));
                assertThat(status(badRequest)).isEqualTo(BAD_REQUEST);
            }

        });
    }

    @Test
    public void testRemovePage() {
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                addPage(page);
                Application.removePage(page.name);
                Result pages = callAction(controllers.routes.ref.Application.getPages());
                Assert.assertTrue(contentAsString(pages).equals("[]"));
            }
        });
    }

    private Result addPage(Page pageToAdd) {

        Request requestMock = Mockito.mock(Request.class);
        RequestBody body = Mockito.mock(RequestBody.class);
        Mockito.when(body.asJson()).thenReturn(pageToAdd.toObjectNode());
        Mockito.when(requestMock.body()).thenReturn(body);

        Context.current.set(new Context(requestMock, new HashMap<String, String>(),
                new HashMap<String, String>()));
        Result result = Application.addPage();
        return result;
    }
}
