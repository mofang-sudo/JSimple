package com.jsimple.community.mapper;


import com.jsimple.community.model.Comment;
import com.jsimple.community.model.Question;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbExtMapper {
    int incLikeCount(Comment comment);

    int incQuestionLikeCount(Question question);

    // Integer count(LikeQueryDTO likeQueryDTO);
}
