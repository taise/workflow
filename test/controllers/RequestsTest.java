package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class RequestsTest {

  @Test
  public void callIndex() {
    Result result = callAction(
        controllers.routes.ref.Requests.index()
        );
    checkResultOk(result);
    assertThat(contentAsString(result)).contains("Request.index");
  }

  @Test
  public void callNewForm() {
    Result result = callAction(
        controllers.routes.ref.Requests.newForm()
        );
    checkResultOk(result);
  }

  public void checkResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
