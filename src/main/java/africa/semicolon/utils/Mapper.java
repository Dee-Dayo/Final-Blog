package africa.semicolon.utils;

import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.User;
import africa.semicolon.dto.requests.CreatePostRequest;
import africa.semicolon.dto.requests.DeletePostRequest;
import africa.semicolon.dto.requests.UserRegisterRequest;
import africa.semicolon.dto.responses.*;

import java.time.format.DateTimeFormatter;

public class Mapper {

    public static User requestMap(UserRegisterRequest userRegisterRequest){
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername().toLowerCase());
        user.setPassword(userRegisterRequest.getPassword());
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        return user;
    }

    public static Post requestMap(CreatePostRequest createPostRequest){
        Post post = new Post();
        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());
        return post;
    }

    public static UserRegisterResponse responseMap(User user){
        UserRegisterResponse response = new UserRegisterResponse();
        response.setUsername(user.getUsername());
        response.setId(user.getId());
        response.setDateCreated(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(user.getDateCreated()));
        return response;
    }

    public static UserLoginResponse loginResponseMap(User user){
        UserLoginResponse response = new UserLoginResponse();
        response.setUsername(user.getUsername());
        response.setId(user.getId());
        return response;
    }

    public static UserLogoutResponse logoutResponseMap(User user){
        UserLogoutResponse response = new UserLogoutResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        return response;
    }

    public static CreatePostResponse createPostResponseMap(Post post){
        CreatePostResponse createPostResponse = new CreatePostResponse();
        createPostResponse.setPostId(post.getId());
        createPostResponse.setPostTitle(post.getTitle());
        createPostResponse.setPostContent(post.getContent());
        return createPostResponse;
    }

    public static DeletePostResponse deletePostResponseMap(Post post){
        DeletePostResponse deletePostResponse = new DeletePostResponse();
        deletePostResponse.setPostId(post.getId());
        deletePostResponse.setPostTitle(post.getTitle());
        return deletePostResponse;
    }
}
