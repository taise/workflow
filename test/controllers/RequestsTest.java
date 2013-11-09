package controllers;

import org.junit.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import play.libs.Yaml;

import play.mvc.*;
import play.test.*;
import com.avaje.ebean.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import models.Request;

public class RequestsTest {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void callIndex() {
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
    params.put("title", "Request test");
    params.put("description", "tryNewRequest");
    params.put("targetDate", "2013-11-03");

    int before_count = Request.count();
    Result result = callAction(
          controllers.routes.ref.Requests.create(),
          fakeRequest().withFormUrlEncodedBody(params)
        );
    assertThat(Request.count()).isEqualTo(before_count + 1);
  }

  @Test
  public void callShow() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    Result result = callAction(controllers.routes.ref.Requests.show(2));
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("create Request 2");
  }

  public void assertResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
