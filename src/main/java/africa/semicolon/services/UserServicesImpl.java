package africa.semicolon.services;

import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.User;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dto.requests.*;
import africa.semicolon.dto.responses.*;
import africa.semicolon.exceptions.InvalidPasswordException;
import africa.semicolon.exceptions.UserAlreadyExistException;
import africa.semicolon.exceptions.UserNotFoundException;
import africa.semicolon.exceptions.UserNotLoggedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semicolon.utils.Mapper.*;

@Service
public class UserServicesImpl implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostServices postServices;

    @Override
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        validateUsername(userRegisterRequest.getUsername());

        User newUser = requestMap(userRegisterRequest);
        userRepository.save(newUser);


        return responseMap(newUser);
    }

    @Override
    public Long countNoOfUsers() {
        return userRepository.count();
    }

    @Override
        public CreatePostResponse createPost(CreatePostRequest createPostRequest) {
            User foundUser = findUserByName(createPostRequest.getAuthor());
            if(!foundUser.isLoggedIn()) throw new UserNotLoggedInException("You need to log in to create post");
            Post post = requestMap(createPostRequest);
            postServices.addPost(post);
            foundUser.getPosts().add(post);
            userRepository.save(foundUser);
            return createPostResponseMap(post);
        }
        @Override
        public DeletePostResponse deletePost(DeletePostRequest deletePostRequest) {
            User foundUser = findUserByName(deletePostRequest.getAuthor());

            if (!foundUser.isLoggedIn()) throw new UserNotLoggedInException("You need to log in to delete post");;

            Post post = postServices.findPostById(deletePostRequest.getPostId());
            postServices.deletePost(post);
            foundUser.getPosts().remove(post);
            userRepository.save(foundUser);
            return deletePostResponseMap(post);
        }

    @Override
    public UserPostsResponse getUserPosts(String username) {
        User user = findUserByName(username);
        List<Post> posts = user.getPosts();
        return allPostResponseMap(user, posts);
    }

    @Override
    public void viewPost(ViewPostRequest viewPostRequest) {
        postServices.addView(viewPostRequest);
    }

    @Override
    public void addComment(CommentPostRequest commentPostRequest) {
        postServices.addComment(commentPostRequest);
    }

    @Override
    public void deleteComment(DeleteCommentRequest deleteCommentREquest) {
        postServices.deleteComment(deleteCommentREquest);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user = findUserByName(userLoginRequest.getUsername().toLowerCase());
        validatePassword(userLoginRequest);
        user.setLoggedIn(true);
        userRepository.save(user);
        return loginResponseMap(user);
    }

    @Override
    public UserLogoutResponse logout(UserLogoutRequest userLogoutRequest) {
        User user = findUserByName(userLogoutRequest.getUsername());
        user.setLoggedIn(false);
        userRepository.save(user);
        return logoutResponseMap(user);
    }

    private void validatePassword(UserLoginRequest userLoginRequest) {
        User user = findUserByName(userLoginRequest.getUsername());
        if(!user.getPassword().equals(userLoginRequest.getPassword())) throw new InvalidPasswordException("Wrong password");
    }


    @Override
    public boolean isUserLoggedIn(String username) {
        User user = findUserByName(username.toLowerCase());
        return user.isLoggedIn();
    }

    @Override
    public int getNoOfUserPosts(String username) {
        User user = findUserByName(username.toLowerCase());
        return user.getPosts().size();
    }

    public User findUserByName(String username) {
        User user = userRepository.findByUsername(username.toLowerCase());
        if (user == null) throw new UserNotFoundException(username + " does not exist");
        return user;
    }

    private void validateUsername(String username) {
        boolean userExists = userRepository.existsByUsername(username.toLowerCase());
        if (userExists) throw new UserAlreadyExistException(username + " already exist");
    }
}
