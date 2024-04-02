package africa.semicolon.services;

import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.User;
import africa.semicolon.dto.requests.*;
import africa.semicolon.dto.responses.*;

import java.util.List;

public interface UserServices {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    Long countNoOfUsers();

    CreatePostResponse createPost(CreatePostRequest createPostRequest);

    UserLoginResponse login(UserLoginRequest userLoginRequest);

    boolean isUserLoggedIn(String username);

    int getNoOfUserPosts(String username);

    UserLogoutResponse logout(UserLogoutRequest userLogoutRequest);

    User findUserByName(String username);

    DeletePostResponse deletePost(DeletePostRequest deletePostRequest);

    UserPostsResponse getUserPosts(String username);

    void viewPost(ViewPostRequest viewPostRequest);

    void addComment(CommentPostRequest commentPostRequest);

    void deleteComment(DeleteCommentRequest deleteCommentREquest);
}
