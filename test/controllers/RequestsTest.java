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
    checkResultOk(result);
    assertThat(contentAsString(result)).contains("Request test");
  }

  @Test
  public void callNewForm() {
    Result result = callAction(
        controllers.routes.ref.Requests.newForm()
        );
    checkResultOk(result);
  }

  @Test
  public void callSave() {
    Map<String,String> params = new HashMap<String,String>();
    params.put("title", "foo");
    params.put("description", "piyo");
    params.put("requestDate", "2013-01-01");

    Result result = callAction(
          controllers.routes.ref.Requests.save(),
          fakeRequest().withFormUrlEncodedBody(params)
        );
  }


  public void checkResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
