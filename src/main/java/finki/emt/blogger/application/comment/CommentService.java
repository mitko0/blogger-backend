package finki.emt.blogger.application.comment;

import finki.emt.blogger.domain.article.ArticleId;
import finki.emt.blogger.domain.authentication.AuthManager;
import finki.emt.blogger.domain.comment.*;
import finki.emt.blogger.domain.user.User;
import finki.emt.blogger.domain.user.UserId;
import finki.emt.blogger.domain.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CommentService implements CommentPort {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentDomainService commentDomainService;
    private final AuthManager authManager;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, CommentDomainService commentDomainService, AuthManager authManager) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.commentDomainService = commentDomainService;
        this.authManager = authManager;
    }

    @Override
    public Page<CommentDto> listCommentsForArticle(String articleId, int page, int size) {

        Page<Comment> comments = commentRepository.findByArticleIdOrderByCreatedAtDesc(new ArticleId(articleId), PageRequest.of(page, size));

        UserId[] creatorIds = comments.stream().map(Comment::getCreatorId).toArray(UserId[]::new);
        List<User> creators = userRepository.findAllById(Arrays.asList(creatorIds));

        CommentDto[] result = comments.stream()
                .map(comment -> {
                    User creator = creators.stream()
                            .filter(user -> user.id().equals(comment.getCreatorId()))
                            .findAny()
                            .orElseThrow();

                    return commentDomainService.mapCommentToDto(comment, creator);
                }).toArray(CommentDto[]::new);

        return new PageImpl<>(Arrays.asList(result));
    }

    @Override
    public CommentDto storeComment(CommentDto commentDto, String jwt) {

        User creator = authManager.getCurrentUser(jwt);

        Comment comment = new Comment(commentDto.content, creator.id(),new ArticleId(commentDto.articleId));
        commentRepository.saveAndFlush(comment);

        return commentDomainService.mapCommentToDto(comment, creator);
    }

    @Override
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        comment.deleteComment();
        commentRepository.saveAndFlush(comment);
    }
}
