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
      assertThat(contentAsString(result)).contains("タイトルが入力されていません。");
    }

    @Test
    public void blankDescription() {
      Map<String,String> params = defaultParams();
      params.remove("description");

      int before_count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains("申請内容が入力されていません。");
    }

    @Test
    public void blankTargetDate() {
      Map<String,String> params = defaultParams();
      params.remove("targetDate");

      int before_count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains("対象日が選択されていません。");
    }

    @Test
    public void blankAll() {
      Map<String,String> params = new HashMap<String,String>();

      int before_count = Request.count();
      Result result = callCreateAction(params);

      assertHasBlank(result, count);
      assertThat(contentAsString(result)).contains("タイトルが入力されていません。");
      assertThat(contentAsString(result)).contains("申請内容が入力されていません。");
      assertThat(contentAsString(result)).contains("対象日が選択されていません。");
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

    public Result callCreateAction(Map<String,String> params) {
      return callAction(
          controllers.routes.ref.Requests.create(),
          fakeRequest().withFormUrlEncodedBody(params)
          );
    }

    public void assertHasBlank(Result result, int count) {
      assertThat(status(result)).isEqualTo(400);
      assertThat(contentAsString(result)).contains("入力内容を確認してください。");
      assertThat(Request.count()).isEqualTo(count);
    }
  }
}
