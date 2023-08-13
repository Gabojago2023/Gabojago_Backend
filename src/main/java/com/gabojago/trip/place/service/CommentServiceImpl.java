package com.gabojago.trip.place.service;

import com.gabojago.trip.place.domain.Comment;
import com.gabojago.trip.place.domain.Place;
import com.gabojago.trip.place.dto.response.CommentResponseDto;
import com.gabojago.trip.place.exception.CommentNotFoundException;
import com.gabojago.trip.place.exception.PlaceNotFoundException;
import com.gabojago.trip.place.repository.CommentRepository;
import com.gabojago.trip.place.repository.PlaceRepository;
import com.gabojago.trip.user.domain.User;
import com.gabojago.trip.user.domain.UserRepository;
import com.gabojago.trip.user.exception.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CommentResponseDto> getCommentsByPlaceId(Integer placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if (place == null) {
            throw new PlaceNotFoundException(placeId.toString());
        }
        return commentRepository.findCommentsByPlaceId(placeId);
    }

    @Override
    @Transactional
    public void updateComment(Integer userId, Integer placeId, Integer commentId, String newCommentText,
            Integer newStartRating) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            throw new CommentNotFoundException("this comment");
        }
        if(comment.getUser().getId().equals(userId)) {
            comment.update(newCommentText, newStartRating);
            commentRepository.save(comment);
            // 업데이트 날짜 반영?
        }
        // 댓글을 유저가 작성하지 않은 경우 업데이트 안함.
    }
}
