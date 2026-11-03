package steps;

import api.Base;
import user.User;
import io.qameta.allure.Step;

import static api.EndPoints.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserSteps {

@Step("Создать пользователя")
    public void userCreate(User user) {
    given()
            .spec(Base.spec())
            .body(user)
            .when()
            .post(USER_CREATE)
            .then()
            .statusCode(SC_OK);
}
@Step("Получить токен")
    public String getAccessToken(User user) {
    return given()
            .spec(Base.spec())
            .body(user)
            .when()
            .post(USER_LOGIN)
            .then()
            .extract()
            .path("accessToken")
            .toString();

}
@Step("Удалить пользователя")
    public void userDelete(String accessToken) {
    given()
            .spec(Base.spec())
            .headers("authorization", accessToken)
            .when()
            .delete(USER_EDIT)
            .then()
            .statusCode(SC_ACCEPTED);
}


}
