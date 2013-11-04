package controllers;

import org.junit.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import play.libs.Yaml;

import play.mvc.*;
import play.test.*;
import com.avaje.ebean.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import models.Request;

public class RequestsTest {

  @Test
  public void callIndex() {
    start(fakeApplication(inMemoryDatabase()));
    Ebean.save((List) Yaml.load("testData/requests.yml"));

    Result result = callAction(
        controllers.routes.ref.Requests.index()
        );
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("Request test");
    assertThat(contentAsString(result)).contains("3件");
  }

  @Test
  public void callNewForm() {
    Result result = callAction(
        controllers.routes.ref.Requests.newForm()
        );
    assertResultOk(result);
  }

  @Test
  public void callCreate() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("title", "foo");
    params.put("description", "piyo");
    params.put("requestDate", "2013-01-01");

    Result result = callAction(
          controllers.routes.ref.Requests.create(),
          fakeRequest().withFormUrlEncodedBody(params)
        );
  }


  public void assertResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
