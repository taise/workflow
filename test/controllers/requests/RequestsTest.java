package controllers.requests;

import org.junit.*;
import java.util.*;
import play.libs.Yaml;

import play.mvc.*;
import play.test.*;
import com.avaje.ebean.*;

import play.data.*;
import static play.data.Form.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

import models.Request;
import models.User;
import play.*;

public class RequestsTest {
  @Before
  public void setUp() {
    start(fakeApplication(inMemoryDatabase()));
  }

  @Test
  public void callIndex() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));

    Result result = callAction(
        controllers.routes.ref.Requests.index(),
        fakeRequest().withSession("email", "steve@email.com")
        );
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("Request test");
    assertThat(contentAsString(result)).contains("3件");
  }

  @Test
  public void callNewForm() {
    Result result = callAction(
        controllers.routes.ref.Requests.newForm(),
        fakeRequest().withSession("email", "alice@email.com")
        );
    assertResultOk(result);
  }

  @Test
  public void callCreate() {
    Map<String,String> params = defaultParams();

    int before_count = Request.count();
    Result result = callAction(
          controllers.routes.ref.Requests.create(),
          fakeRequest().withFormUrlEncodedBody(params)
                       .withSession("email", "alice@email.com")
        );

    assertThat(Request.count()).isEqualTo(before_count + 1);
  }

  @Test
  public void callShow() {
    Ebean.save((List) Yaml.load("testData/requests.yml"));
    Result result = callAction(
          controllers.routes.ref.Requests.show(2),
          fakeRequest().withSession("email", "alice@email.com")
        );
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("create Request 2");
  }

  public Map<String,String> defaultParams() {
    Map<String,String> params = new HashMap<String,String>();
    User user = User.find.findUnique();
    params.put("title", "Request test");
    params.put("description", "tryNewRequest");
    params.put("targetDate", "2013-11-03");
    params.put("requester.id", user.id.toString());
    return params;
  }

  public void assertResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
