package com.gabojago.trip.place.repository;

import com.gabojago.trip.place.dto.response.CommentResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = "local")
@Transactional()
class CommentRepositoryTest {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Test
    void findCommentsByPlaceId() {
        Pageable pageable = Pageable.ofSize(11);
        Integer placeId = 125266;
        Integer cursor = null;

        List<CommentResponseDto> commentsByPlaceId = commentRepository.findCommentsByPlaceId(
                placeId, pageable);

        pageable = Pageable.ofSize(10);
        Slice<CommentResponseDto> commentSlice = checkLastPage(pageable, commentsByPlaceId);
        System.out.println(commentSlice.getContent());
        System.out.println(commentSlice.get());
        System.out.println(commentSlice.getPageable());
        System.out.println(commentSlice.hasNext());
    }

    private Slice<CommentResponseDto> checkLastPage(Pageable pageable,
            List<CommentResponseDto> commentsByPlaceId) {
        boolean hasNext = false; //다음으로 가져올 데이터가 있는 지 여부를 알려줌
        if (commentsByPlaceId.size() > pageable.getPageSize()) {
            hasNext = true; //읽어 올 데이터가 있다면 true를 반환
            commentsByPlaceId.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(commentsByPlaceId, pageable, hasNext);
    }

    @Test
    void findNextCommentsByPlaceId() {
        Pageable pageable = Pageable.ofSize(10);
        Integer placeId = 125266;
        Integer cursor = 21;

        List<CommentResponseDto> commentsByPlaceId = commentRepository.findNextCommentsByPlaceId(
                placeId, cursor, pageable);

        for (CommentResponseDto commentResponseDto : commentsByPlaceId) {
            System.out.println(commentResponseDto);
        }
    }
}