package com.mobi.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobi.service.UserService;
import com.mobi.service.models.PostDetail;
import com.mobi.service.models.UserDetail;
import com.mobi.utils.common.TestDataHolder;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.mobi.utils.common.AssertUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceBlogTest {

	private static TestDataHolder dataHolder;
	private List<PostDetail> postDetails = new ArrayList<>();

	@BeforeClass
	public static void setup() {
		dataHolder = new TestDataHolder();
	}

	@Test()
	public void searchForTheGivenUsernameTest() throws MalformedURLException, JsonProcessingException {
		UserDetail matchedUserDetails = findTheUserIdForUser("Delphine");
		assertThat(matchedUserDetails)
				.describedAs("user Delphine is not found")
				.isNotNull();
		final int userId = matchedUserDetails.getId();
		assertThat(userId)
				.describedAs("userid is not matched")
				.isEqualTo(9);
	}

	@Test
	public void searchForThePostsWrittenByTheUserTest() throws MalformedURLException, JsonProcessingException {
		UserDetail matchedUserDetails = findTheUserIdForUser("Delphine");
		final Response usersPostResponse = UserService.getPosts();
		assertForSuccessFulResponse(usersPostResponse, "Post service is failed to retrieve user post details");
		postDetails = findAllMatchingPostByUserId(usersPostResponse, matchedUserDetails.getId());
		dataHolder.setPostDetails(postDetails);
		assertThat(postDetails.size())
				.describedAs("user post details are not matched")
				.isGreaterThanOrEqualTo(1);
	}

	@Test
	public void forEachPostFetchTheCommentsAndValidateTest() throws MalformedURLException, JsonProcessingException {
		searchForThePostsWrittenByTheUserTest();
		final Response usersCommentsResponse = UserService.getComments();
		assertForSuccessFulResponse(usersCommentsResponse, "comment service is failed to retrieve user comment details");
		assertForCommentsEmailAddressIsValid(usersCommentsResponse, dataHolder);
	}
}
