/**
 * Spring Boot Practical Interview Problems
 * Commonly Asked by: Amazon, Google, Microsoft, Facebook, Netflix, Uber, etc.
 * 
 * Topics covered:
 * 1. REST API design
 * 2. Database operations (JPA/Hibernate)
 * 3. Service layer patterns
 * 4. Exception handling
 * 5. Testing
 * 6. Concurrency & thread safety
 * 7. Caching
 * 8. Configuration & properties
 * 9. Security
 * 10. Real-world scenarios
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.persistence.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// ============================================================================
// 1. ENTITY CLASSES
// ============================================================================

/**
 * Problem 1: Design a User entity with proper JPA annotations
 * Company: Amazon, Google, Microsoft
 * Difficulty: Easy
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_email", columnList = "email", unique = true),
    @Index(name = "idx_username", columnList = "username")
})
class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;  // In real app, this should be hashed
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts = new HashSet<>();
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.status = UserStatus.ACTIVE;
    }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    // ... more getters/setters
}

enum UserStatus {
    ACTIVE, INACTIVE, SUSPENDED, DELETED
}

@Entity
@Table(name = "posts")
class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "view_count")
    private Integer viewCount = 0;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Post() {}
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public Integer getViewCount() { return viewCount; }
    public void incrementViewCount() { this.viewCount++; }
}

// ============================================================================
// 2. REPOSITORY LAYER (DAO)
// ============================================================================

/**
 * Problem 2: Implement custom repository with query methods
 * Company: Amazon, Google, Microsoft
 * Difficulty: Easy-Medium
 */
@Repository
interface UserRepository extends JpaRepository<User, Long> {
    
    // Basic queries
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    List<User> findByStatus(UserStatus status);
    
    // Complex queries
    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    // Custom JPQL queries
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findUserByName(String username);
    
    // Native SQL queries
    @Query(value = "SELECT * FROM users WHERE status = :status ORDER BY created_at DESC", 
           nativeQuery = true)
    List<User> findRecentUsersByStatus(String status);
    
    // Check existence
    boolean existsByEmail(String email);
    
    // Counting
    long countByStatus(UserStatus status);
}

@Repository
interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByUserId(Long userId);
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.viewCount DESC")
    List<Post> findPopularPostsByUser(Long userId);
}

// ============================================================================
// 3. SERVICE LAYER (Business Logic)
// ============================================================================

/**
 * Problem 3: Implement service with proper exception handling and transactions
 * Company: Amazon, Google, Microsoft, Facebook
 * Difficulty: Medium
 */
@Service
@Validated
class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    /**
     * Create new user with validation
     * Throws: UserAlreadyExistsException, InvalidInputException
     */
    @Transactional
    public User createUser(String username, String email, String password) {
        
        // Validation
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidInputException("Username cannot be empty");
        }
        
        if (!email.contains("@")) {
            throw new InvalidInputException("Invalid email format");
        }
        
        if (password.length() < 8) {
            throw new InvalidInputException("Password must be at least 8 characters");
        }
        
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already registered: " + email);
        }
        
        User user = new User(username, email, password);
        // In real app: user.setPassword(passwordEncoder.encode(password));
        
        return userRepository.save(user);
    }
    
    /**
     * Get user by ID with error handling
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }
    
    /**
     * Get user by email with optional
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Update user
     */
    @Transactional
    public User updateUser(Long id, String email, String username) {
        User user = getUserById(id);
        
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new UserAlreadyExistsException("Email already in use");
            }
            user.email = email;
        }
        
        if (username != null && !username.isEmpty()) {
            user.username = username;
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Delete user
     */
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    /**
     * Get all active users
     */
    @Cacheable("activeUsers")
    public List<User> getActiveUsers() {
        return userRepository.findByStatus(UserStatus.ACTIVE);
    }
    
    /**
     * Clear cache (called when new user added)
     */
    @CacheEvict(value = "activeUsers", allEntries = true)
    @Transactional
    public void invalidateUserCache() {
        // Cache will be rebuilt on next call
    }
}

@Service
class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create post with validation
     */
    @Transactional
    public Post createPost(Long userId, String title, String content) {
        
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidInputException("Title cannot be empty");
        }
        
        User user = userService.getUserById(userId);
        Post post = new Post(title, content, user);
        
        return postRepository.save(post);
    }
    
    /**
     * Get post with view count increment
     */
    @Transactional
    public Post getPostWithViews(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));
        
        post.incrementViewCount();
        return postRepository.save(post);
    }
    
    /**
     * Get user's posts
     */
    public List<Post> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    /**
     * Search posts
     */
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }
}

// ============================================================================
// 4. REST CONTROLLER
// ============================================================================

/**
 * Problem 4: Design RESTful API with proper HTTP methods and status codes
 * Company: All companies
 * Difficulty: Medium
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * GET /api/users/{id}
     * Retrieve user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }
    
    /**
     * POST /api/users
     * Create new user
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(
            request.getUsername(),
            request.getEmail(),
            request.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }
    
    /**
     * PUT /api/users/{id}
     * Update user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        
        User user = userService.updateUser(id, request.getEmail(), request.getUsername());
        return ResponseEntity.ok(new UserDTO(user));
    }
    
    /**
     * DELETE /api/users/{id}
     * Delete user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * GET /api/users
     * List all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<User> users = userService.getActiveUsers();
        List<UserDTO> userDTOs = users.stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
    
    /**
     * GET /api/users/email/{email}
     * Find user by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(new UserDTO(user));
    }
}

@RestController
@RequestMapping("/api/posts")
class PostController {
    
    @Autowired
    private PostService postService;
    
    /**
     * POST /api/posts
     * Create new post
     */
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody CreatePostRequest request) {
        Post post = postService.createPost(
            request.getUserId(),
            request.getTitle(),
            request.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostDTO(post));
    }
    
    /**
     * GET /api/posts/{id}
     * Get post (increments view count)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        Post post = postService.getPostWithViews(id);
        return ResponseEntity.ok(new PostDTO(post));
    }
    
    /**
     * GET /api/users/{userId}/posts
     * Get all posts by user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable Long userId) {
        List<Post> posts = postService.getUserPosts(userId);
        List<PostDTO> postDTOs = posts.stream()
            .map(PostDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }
    
    /**
     * GET /api/posts/search
     * Search posts by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<PostDTO>> searchPosts(@RequestParam String keyword) {
        List<Post> posts = postService.searchPosts(keyword);
        List<PostDTO> postDTOs = posts.stream()
            .map(PostDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }
}

// ============================================================================
// 5. DTOs (Data Transfer Objects)
// ============================================================================

/**
 * Problem 5: Design DTOs for API responses
 * Company: Amazon, Google, Microsoft
 * Difficulty: Easy
 */
class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private UserStatus status;
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.status = user.status;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}

class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
    private Integer viewCount;
    
    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorUsername = post.getUser().getUsername();
        this.createdAt = post.createdAt;
        this.viewCount = post.getViewCount();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthorUsername() { return authorUsername; }
}

// ============================================================================
// 6. REQUEST/RESPONSE CLASSES
// ============================================================================

class CreateUserRequest {
    private String username;
    private String email;
    private String password;
    
    public CreateUserRequest() {}
    public CreateUserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

class UpdateUserRequest {
    private String username;
    private String email;
    
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}

class CreatePostRequest {
    private Long userId;
    private String title;
    private String content;
    
    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}

// ============================================================================
// 7. EXCEPTION HANDLING
// ============================================================================

/**
 * Problem 6: Custom exceptions and global exception handler
 * Company: Amazon, Google, Microsoft, Facebook
 * Difficulty: Medium
 */

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}

class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}

// Global Exception Handler
@RestControllerAdvice
class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse error = new ErrorResponse("USER_NOT_FOUND", e.getMessage(), 
                                               LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException e) {
        ErrorResponse error = new ErrorResponse("USER_ALREADY_EXISTS", e.getMessage(), 
                                               LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException e) {
        ErrorResponse error = new ErrorResponse("INVALID_INPUT", e.getMessage(), 
                                               LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
    
    public ErrorResponse(String code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

// ============================================================================
// 8. SPRING BOOT APPLICATION
// ============================================================================

@SpringBootApplication
public class BlogApplicationDemo {
    
    public static void main(String[] args) {
        SpringApplication.run(BlogApplicationDemo.class, args);
    }
}

// ============================================================================
// INTERVIEW TIPS
// ============================================================================

/*
COMMON SPRING BOOT INTERVIEW QUESTIONS:

1. Explain dependency injection and how Spring implements it
   → Answer: Spring uses inversion of control via @Autowired, @Component, etc.

2. What's the difference between @Service, @Repository, @Component?
   → Service: Business logic, Repository: Data access, Component: General beans

3. How does @Transactional work?
   → Manages database transactions; rolls back on exception

4. What are the different scopes of Spring beans?
   → Singleton (default), Prototype, Request, Session, Global Session

5. Explain REST principles
   → Client-Server, Stateless, Cacheable, Uniform Interface, Layered System

6. How to handle exceptions in Spring?
   → @RestControllerAdvice, @ExceptionHandler

7. What's the difference between PUT and POST?
   → POST: Create new resource, PUT: Update existing resource

8. How to optimize Spring Boot applications?
   → Database indexing, Caching (@Cacheable), Connection pooling, Lazy loading

9. What's the N+1 problem in Hibernate?
   → Loading one parent loads N children separately instead of with JOIN
   → Fix: Use FetchType.EAGER or @Query with JOIN FETCH

10. How to implement authentication and authorization?
    → Spring Security, @EnableWebSecurity, SecurityFilterChain
*/
