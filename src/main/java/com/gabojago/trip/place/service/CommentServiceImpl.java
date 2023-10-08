package com.gabojago.trip.place.service;

import com.gabojago.trip.place.domain.Comment;
import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import com.gabojago.trip.place.exception.CommentNotFoundException;
import com.gabojago.trip.place.exception.PlaceNotFoundException;
import com.gabojago.trip.place.exception.UnauthorizedCommentDeletionException;
import com.gabojago.trip.place.repository.CommentRepository;
import com.gabojago.trip.place.repository.PlaceRepository;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import com.gabojago.trip.user.exception.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PlaceRepository placeRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addCommentToPlace(Integer placeId, Integer userId, String commentText,
            Integer starRating) {
        Place place = placeRepository.findById(placeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (place == null) {
            throw new PlaceNotFoundException(placeId.toString());
        } else if (user == null) {
            throw new UserNotFoundException("존재하지 않는 유저 : " + userId);
        } else {
            Comment comment = new Comment(place, user, commentText, starRating);
            commentRepository.save(comment);
        }
    }

    @Override
    public Slice<CommentResponseDto> getCommentsByPlaceId(Integer placeId,
            Integer cursor, Integer size) {
        // 장소가 존재하지 않는 경우 예외처리
        Place place = placeRepository.findById(placeId).orElse(null);
        if (place == null) {
            throw new PlaceNotFoundException(placeId.toString());
        }

        Pageable pageable = Pageable.ofSize(size + 1);

        if (cursor == null) {
            List<CommentResponseDto> commentList = commentRepository.findCommentsByPlaceId(placeId,
                    pageable);
            pageable = Pageable.ofSize(size);
            return checkLastPage(pageable, commentList);
        } else {
            List<CommentResponseDto> commentList = commentRepository.findNextCommentsByPlaceId(
                    placeId, cursor, pageable);
            pageable = Pageable.ofSize(size);
            return checkLastPage(pageable, commentList);
        }
    }

    private Slice<CommentResponseDto> checkLastPage(Pageable pageable,
            List<CommentResponseDto> commentList) {
        boolean hasNext = false; //다음으로 가져올 데이터가 있는 지 여부를 알려줌
        if (commentList.size() > pageable.getPageSize()) {
            hasNext = true; //읽어 올 데이터가 있다면 true를 반환
            commentList.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(commentList, pageable, hasNext);
    }

    @Override
    @Transactional
    public void updateComment(Integer userId, Integer placeId, Integer commentId,
            String newCommentText,
            Integer newStartRating) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException("this comment");
        }
        if (!comment.getUser().getId().equals(userId)) {
            // 댓글 작성자와 주어진 userId가 다를 경우 처리
            throw new UnauthorizedCommentDeletionException(userId + " : " + commentId);
        }
        comment.update(newCommentText, newStartRating);
        commentRepository.save(comment);
        // 업데이트 날짜 반영?

    }

    @Override
    public void deleteCommentById(Integer userId, Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException("this comment");
        }
        if (!comment.getUser().getId().equals(userId)) {
            // 댓글 작성자와 주어진 userId가 다를 경우 처리
            throw new UnauthorizedCommentDeletionException(userId + " : " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}
