package com.nyasba.jwt.apiauth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static com.nyasba.jwt.apiauth.support.SecurityConstants.LOGIN_URL;
import static com.nyasba.jwt.apiauth.support.SecurityConstants.SIGNUP_URL;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiAuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiAuthApplicationTests {

	@LocalServerPort
	int port;

	@Before
	public void setup() {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Ignore // token作るために使っているだけなのでテストではない
	@Test
    public void ユーザ登録してPWを暗号化する() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("loginId", "nyasba")
                .put("pass", "password");

        given().body(jsonObject.toString())
                .contentType(ContentType.JSON)
                .post(SIGNUP_URL)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

	@Test
	public void 認証成功してトークンが取れること() throws JSONException {

		JSONObject jsonObject = new JSONObject()
				.put("loginId", "nyasba")
				.put("pass", "password");

		given().body(jsonObject.toString())
				.contentType(ContentType.JSON)
				.post(LOGIN_URL)
				.then()
				.statusCode(HttpStatus.OK.value())
				.header("Authorization", Matchers.startsWith("Bearer "));
	}

    @Test
    public void 認証失敗してトークンが取れないこと() throws JSONException {

        JSONObject jsonObject = new JSONObject()
                .put("loginId", "nyasba")
                .put("pass", "notvalid");

        given().body(jsonObject.toString())
                .contentType(ContentType.JSON)
                .post(LOGIN_URL)
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
