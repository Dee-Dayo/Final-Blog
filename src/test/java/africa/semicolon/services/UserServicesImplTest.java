package africa.semicolon.services;

import africa.semicolon.data.models.Post;
import africa.semicolon.data.models.User;
import africa.semicolon.data.repositories.PostRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.data.repositories.ViewRepository;
import africa.semicolon.dto.requests.*;
import africa.semicolon.exceptions.InvalidPasswordException;
import africa.semicolon.exceptions.UserAlreadyExistException;
import africa.semicolon.exceptions.UserNotLoggedInException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServicesImplTest {

    @Autowired
    UserServices userServices;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostServices postServices;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ViewRepository viewRepository;
    @Autowired
    ViewServices viewServices;
    @Autowired
    CommentServices commentServices;


    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        viewRepository.deleteAll();
    }

    @Test
    public void registerOneUser_userCountIsOne(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);

        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerOneUser_anotherUserCantUseSameUsername_throwException(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1, userRepository.count());

        assertThrows(UserAlreadyExistException.class, ()->userServices.register(userRegisterRequest));
    }

    @Test
    public void registerOneUser_userCanLogin(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1, userServices.countNoOfUsers());
        assertFalse(userServices.isUserLoggedIn("username"));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);

        assertTrue(userServices.isUserLoggedIn("useRNAME"));
    }

    @Test
    public void registerOneUser_userCantLoginWithWrongDetails(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1, userServices.countNoOfUsers());
        assertFalse(userServices.isUserLoggedIn("username"));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("userNAME");
        userLoginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, ()->userServices.login(userLoginRequest));
    }

    @Test
    public void registerOneUser_userCanLogin_userCanLogout(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1, userServices.countNoOfUsers());
        assertFalse(userServices.isUserLoggedIn("username"));

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);

        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        UserLogoutRequest userLogoutRequest = new UserLogoutRequest();
        userLogoutRequest.setUsername("username");
        userServices.logout(userLogoutRequest);
        assertFalse(userServices.isUserLoggedIn("username"));
    }

    @Test
    public void registerOneUser_userCanCreatePost(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        userServices.createPost(createPostRequest);
        assertEquals(1, userServices.getNoOfUserPosts("username"));
        assertEquals(1, postServices.countNoOfPosts());
    }

    @Test
    public void registerOneUser_userCantCreatePostWithoutLogin_throwsException(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        assertThrows(UserNotLoggedInException.class, ()->userServices.createPost(createPostRequest));
        assertEquals(0, userServices.getNoOfUserPosts("username"));
    }

    @Test
    public void registerOneUser_userCanCreatePost_userCanDeletePost(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        userServices.createPost(createPostRequest);
        assertEquals(1, userServices.getNoOfUserPosts("username"));
        assertEquals(1, postServices.countNoOfPosts());

        User user = userServices.findUserByName("username");

        DeletePostRequest deletePostRequest = new DeletePostRequest();
        deletePostRequest.setAuthor("username");
        deletePostRequest.setPostId(user.getPosts().get(0).getId());
        userServices.deletePost(deletePostRequest);

        assertEquals(0, postServices.countNoOfPosts());
        assertEquals(0, userServices.getNoOfUserPosts("username"));
    }

    @Test
    public void userRegister_create3Post_userCanFindAllPost(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        userServices.createPost(createPostRequest);
        userServices.createPost(createPostRequest);
        userServices.createPost(createPostRequest);
        assertEquals(3, userServices.getNoOfUserPosts("username"));

        User user = userServices.findUserByName("username");

        List<Post> posts = user.getPosts();
        assertEquals(posts.size(), userServices.getNoOfUserPosts("username"));
    }

    @Test
    public void onePostCreated_userCanViewPost(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        userServices.createPost(createPostRequest);
        assertEquals(1, postServices.countNoOfPosts());

        User user = userServices.findUserByName("username");
        Post post = postServices.findPostById(user.getPosts().get(0).getId());
        assertEquals(0, post.getViews().size());

        ViewPostRequest viewPostRequest = new ViewPostRequest();
        viewPostRequest.setViewer(user);
        viewPostRequest.setPostId(post.getId());
        userServices.viewPost(viewPostRequest);

        post = postServices.findPostById(user.getPosts().get(0).getId());
        assertEquals(1, post.getViews().size());
        assertEquals(1L, viewServices.countNoOfViews());
    }

    @Test
    public void onePostCreated_userCanCommentOnPost(){
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");
        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");
        userServices.createPost(createPostRequest);
        assertEquals(1, postServices.countNoOfPosts());

        User user = userServices.findUserByName("username");
        Post post = postServices.findPostById(user.getPosts().get(0).getId());
        assertEquals(0, post.getViews().size());

        CommentPostRequest commentPostRequest = new CommentPostRequest();
        commentPostRequest.setCommenter(user);
        commentPostRequest.setPostId(post.getId());
        commentPostRequest.setComment("Comment on this post");
        userServices.addComment(commentPostRequest);


        post = postServices.findPostById(user.getPosts().get(0).getId());
        assertEquals(1, post.getComments().size());
        assertEquals(1L, commentServices.countNoOfViews());
    }
}