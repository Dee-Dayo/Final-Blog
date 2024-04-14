package africa.semicolon.services;

import africa.semicolon.data.models.Comment;
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

    private  UserRegisterRequest userRegisterRequest;
    private UserLoginRequest userLoginRequest;
    private CreatePostRequest createPostRequest;
    private ViewPostRequest viewPostRequest;
    private CommentPostRequest commentPostRequest;





    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        viewRepository.deleteAll();

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setFirstName("Firstname");
        userRegisterRequest.setLastName("Lastname");
        userRegisterRequest.setPassword("password");
        userRegisterRequest.setUsername("username");

        userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername("username");
        userLoginRequest.setPassword("password");

        createPostRequest = new CreatePostRequest();
        createPostRequest.setAuthor("username");
        createPostRequest.setTitle("Title");
        createPostRequest.setContent("Content");

        viewPostRequest = new ViewPostRequest();
        commentPostRequest = new CommentPostRequest();
    }

    @Test
    public void registerOneUser_userCountIsOne(){
        userServices.register(userRegisterRequest);
        assertEquals(1, userRepository.count());
    }

    @Test
    public void registerOneUser_anotherUserCantUseSameUsername_throwException(){
        userServices.register(userRegisterRequest);
        assertEquals(1, userRepository.count());
        assertThrows(UserAlreadyExistException.class, ()->userServices.register(userRegisterRequest));
    }

    @Test
    public void registerOneUser_userCanLogin(){
        userServices.register(userRegisterRequest);
        assertEquals(1, userServices.countNoOfUsers());
        assertFalse(userServices.isUserLoggedIn("username"));

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));
    }

    @Test
    public void registerOneUser_userCantLoginWithWrongDetails(){
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
        userServices.register(userRegisterRequest);
        assertEquals(1, userServices.countNoOfUsers());
        assertFalse(userServices.isUserLoggedIn("username"));

        userServices.login(userLoginRequest);

        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        UserLogoutRequest userLogoutRequest = new UserLogoutRequest();
        userLogoutRequest.setUsername("username");
        userServices.logout(userLogoutRequest);
        assertFalse(userServices.isUserLoggedIn("username"));
    }

    @Test
    public void registerOneUser_userCanCreatePost(){
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        userServices.createPost(createPostRequest);
        assertEquals(1, userServices.getNoOfUserPosts("username"));
        assertEquals(1, postServices.countNoOfPosts());
    }

    @Test
    public void registerOneUser_userCantCreatePostWithoutLogin_throwsException(){
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        assertThrows(UserNotLoggedInException.class, ()->userServices.createPost(createPostRequest));
        assertEquals(0, userServices.getNoOfUserPosts("username"));
    }

    @Test
    public void registerOneUser_userCanCreatePost_userCanDeletePost(){
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

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
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

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
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        userServices.createPost(createPostRequest);
        assertEquals(1, postServices.countNoOfPosts());

        User user = userServices.findUserByName("username");
        Post post = postServices.findPostById(user.getPosts().getFirst().getId());
        assertEquals(0, post.getViews().size());

        viewPostRequest.setViewer(user);
        viewPostRequest.setPostId(post.getId());
        userServices.viewPost(viewPostRequest);

        post = postServices.findPostById(user.getPosts().getFirst().getId());
        assertEquals(1, post.getViews().size());
        assertEquals(1L, viewServices.countNoOfViews());
    }

    @Test
    public void onePostCreated_userCanCommentOnPost(){
        userServices.register(userRegisterRequest);
        assertEquals(1L, userServices.countNoOfUsers());

        userServices.login(userLoginRequest);
        assertTrue(userServices.isUserLoggedIn("useRNAME"));

        userServices.createPost(createPostRequest);
        assertEquals(1, postServices.countNoOfPosts());
 
        User user = userServices.findUserByName("username");
        Post post = postServices.findPostById(user.getPosts().getFirst().getId());
        assertEquals(0, post.getViews().size());

        commentPostRequest.setCommenter(user);
        commentPostRequest.setPostId(post.getId());
        commentPostRequest.setComment("Comment on this post");
        userServices.addComment(commentPostRequest);


        post = postServices.findPostById(user.getPosts().getFirst().getId());
        assertEquals(1, post.getComments().size());
        assertEquals(1L, commentServices.countNoOfViews());
    }

     @Test
    public void onePostCreated_userCanCommentOnPost_userCanDeleteComment(){
        userServices.register(userRegisterRequest);

        userServices.login(userLoginRequest);

        userServices.createPost(createPostRequest);

        User user = userServices.findUserByName("username");
        Post post = postServices.findPostById(user.getPosts().get(0).getId());

        commentPostRequest.setCommenter(user);
        commentPostRequest.setPostId(post.getId());
        commentPostRequest.setComment("Comment on this post");
        userServices.addComment(commentPostRequest);


        post = postServices.findPostById(user.getPosts().get(0).getId());

        Comment comment = commentServices.findCommentById(post.getComments().get(0).getId());

        DeleteCommentRequest deleteCommentREquest = new DeleteCommentRequest();
        deleteCommentREquest.setPostId(post.getId());
        deleteCommentREquest.setCommentId(comment.getId());
        userServices.deleteComment(deleteCommentREquest);

        post = postServices.findPostById(user.getPosts().get(0).getId());
        assertEquals(0, post.getComments().size());
        assertEquals(0, commentServices.countNoOfViews());
    }
}