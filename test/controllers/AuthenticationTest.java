package controllers;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import com.avaje.ebean.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;

public class AuthenticationTest {

  @Test
  public void CallLoginForm() {
    Result result = callAction(
        controllers.routes.ref.Authentication.loginForm()
        );
    assertResultOk(result);
    assertThat(contentAsString(result)).contains("Email");
    assertThat(contentAsString(result)).contains("Password");
    assertThat(contentAsString(result)).contains("ログイン");
    assertNull(session(result).get("email"));
  }

  public void assertResultOk(Result result) {
    assertThat(status(result)).isEqualTo(OK);
    assertThat(contentType(result)).isEqualTo("text/html");
    assertThat(charset(result)).isEqualTo("utf-8");
  }
}
