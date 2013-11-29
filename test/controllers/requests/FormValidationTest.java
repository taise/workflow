package controllers.requests;

import org.junit.*;
import java.util.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import play.mvc.*;

import static org.fest.assertions.Assertions.*;
import static org.hamcrest.Matchers.*;
import static play.test.Helpers.*;

import models.Request;
import models.User;

@RunWith(Enclosed.class)
public class FormValidationTest {

  public static class ValidationFail {

    public static final String TITLE = "Request title test";
    public static final String DESCRIPTION = "Request description test.\nThis is other line.";
    public static final String TARGETDATE = "2013-11-03";

    @Before
    public void setUp() {
      start(fakeApplication(inMemoryDatabase()));
    }

    @Test
    public void blankTitle() {
      Map<String,String> params = defaultParams();
      params.remove("title");

      int count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains(DESCRIPTION);
      assertThat(contentAsString(result)).contains(TARGETDATE);
      assertThat(contentAsString(result)).contains("タイトルが入力されていません。");
    }

    @Test
    public void blankDescription() {
      Map<String,String> params = defaultParams();
      params.remove("description");

      int count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains(TITLE);
      assertThat(contentAsString(result)).contains(TARGETDATE);
      assertThat(contentAsString(result)).contains("申請内容が入力されていません。");
    }

    @Test
    public void blankTargetDate() {
      Map<String,String> params = defaultParams();
      params.remove("targetDate");

      int count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains(TITLE);
      assertThat(contentAsString(result)).contains(DESCRIPTION);
      assertThat(contentAsString(result)).contains("対象日が選択されていません。");
    }

    @Test
    public void blankAll() {
      Map<String,String> params = new HashMap<String,String>();

      int count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains("タイトルが入力されていません。");
      assertThat(contentAsString(result)).contains("申請内容が入力されていません。");
      assertThat(contentAsString(result)).contains("対象日が選択されていません。");
    }


    public Map<String,String> defaultParams() {
      Map<String,String> params = new HashMap<String,String>();
      User user = User.find.findUnique();
      params.put("title", TITLE);
      params.put("description", DESCRIPTION);
      params.put("targetDate", TARGETDATE);
      params.put("requester.id", user.id.toString());
      return params;
    }

    public Result callCreateAction(Map<String,String> params) {
      return callAction(
          controllers.routes.ref.Requests.create(),
          fakeRequest().withFormUrlEncodedBody(params)
                       .withSession("email", "alice@email.com")
          );
    }

    public void assertHasBlank(Result result, int count) {
      assertThat(status(result)).isEqualTo(400);
      assertThat(contentAsString(result)).contains("入力内容を確認してください。");
      assertThat(Request.count()).isEqualTo(count);
    }
  }
}
