package com.mobi.utils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobi.service.UserService;
import com.mobi.service.models.CommentDetail;
import com.mobi.service.models.PostDetail;
import com.mobi.service.models.UserDetail;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.validator.routines.EmailValidator.getInstance;
import static org.assertj.core.api.Assertions.assertThat;

public class AssertUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static UserDetail findTheMatchingUser(Response response, String matcherName) throws JsonProcessingException {
        String responseInString = response.getBody().prettyPrint();
        final List<UserDetail> userDetails = objectMapper.readValue(responseInString,
                new TypeReference<List<UserDetail>>(){});
        return userDetails.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(matcherName))
                .findFirst()
                .orElse(null);
    }

    public static void assertForSuccessFulResponse(Response response, String message) {
        assertThat(response.getStatusCode())
                .describedAs(message)
                .isEqualTo(HttpStatus.SC_OK);
    }

    public static void assertForCommentsEmailAddressIsValid(Response usersCommentsResponse, TestDataHolder dataHolder)
            throws JsonProcessingException {
        final List<CommentDetail> commentDetails = objectMapper.readValue(usersCommentsResponse.getBody().print(),
                new TypeReference<List<CommentDetail>>(){});
        List<PostDetail> postDetails = dataHolder.getPostDetails();
        for(PostDetail post : postDetails) {
            for(CommentDetail comment : commentDetails){
                if(post.getId() == comment.getId()) {
                    assertThat(getInstance().isValid(comment.getEmail()))
                            .describedAs(String.format("Email address is invalid for %d", comment.getId())).isTrue();
                            //.describedAs("Email address is valid").isTrue();
                }
            }
        }
    }

    public static List<PostDetail> findAllMatchingPostByUserId(Response usersPostResponse, int matchedUserId)
            throws JsonProcessingException {
        String responseInString = usersPostResponse.getBody().prettyPrint();
        final List<PostDetail> postDetails = objectMapper.readValue(responseInString,
                new TypeReference<List<PostDetail>>(){});
        return postDetails.stream()
                .filter(details -> details.getUserId() == matchedUserId)
                .collect(Collectors.toList());
    }

    public static UserDetail findTheUserIdForUser(String user) throws MalformedURLException, JsonProcessingException {
        final Response usersResponse = UserService.getUsers();
        assertForSuccessFulResponse(usersResponse, "Users service is failed to retrieve user details");
        return findTheMatchingUser(usersResponse,user);
    }

}
