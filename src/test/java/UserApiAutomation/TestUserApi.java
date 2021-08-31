package UserApiAutomation;
import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import pojo.ResponseData;

public class TestUserApi {

	String UserId;

	String accessToken = "Bearer 4dd54d9e6f9c85a759807741ffcacf2f5eea09b17311088dc9b1137b91322b8e";

	@Test(priority = 1)
	public void createUser() {
		RestAssured.baseURI = "https://gorest.co.in/public/v1/users";
		File createUserPayload = new File("src/test/resources/Payloads/createUser.json");
		ResponseData responseData=RestAssured.given()
		.log().all()
		.header("Authorization", accessToken)
		.header("Content-Type", "application/json")
		.body(createUserPayload)
		.expect()
		.defaultParser(Parser.JSON)
		.when()
		.post()
		.as(ResponseData.class);
		 UserId = responseData.getData().getId();
		 String actualName=responseData.getData().getName();
		 String expectedName="Abhishek";
		 Assert.assertEquals(expectedName,actualName,"Name should match");

	}

	@Test(priority = 2)
	public void updateUser() {
		File updatePayload = new File("src/test/resources/Payloads/updatePayload.json");
         RestAssured.given()
		.log().all()
		.header("Authorization", accessToken)
		.header("Content-Type", "application/json")
		.body(updatePayload)
		.when()
		.put(UserId)
		.then()
		.log().all()
		.assertThat()
		.statusCode(200)
		.header("Content-Encoding", "gzip");

	}

	@Test(priority = 3)
	public void getUser() {
         RestAssured.given()
		.log().all()
		.when()
		.get(UserId)
		.then()
		.log().all()
		.assertThat()
		.statusCode(200)
	    .header("Content-Encoding", "gzip");
	}

	@Test(priority = 4)
	public void deleteUser() {
         RestAssured.given()
		.log().all()
		.header("Authorization", accessToken)
		.header("Content-Type", "application/json")
		.when()
		.delete(UserId)
		.then()
		.log().all()
		.assertThat()
		.statusCode(204)
		.header("Cache-Control","no-cache");
	}

}
