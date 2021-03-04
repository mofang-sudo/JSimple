package com.jsimple.community.service;

import com.jsimple.community.dto.ResultDTO;
import com.jsimple.community.dto.ThumbDTO;
import com.jsimple.community.enums.CommentTypeEnum;
import com.jsimple.community.enums.LikeTypeEnum;
import com.jsimple.community.enums.NotificationStatusEnum;
import com.jsimple.community.enums.NotificationTypeEnum;
import com.jsimple.community.exception.CustomizeErrorCode;
import com.jsimple.community.exception.CustomizeException;
import com.jsimple.community.mapper.*;
import com.jsimple.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private ThumbMapper thumbMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ThumbExtMapper thumbExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    public int queryLike(Long targetId, Integer type, Long liker) {

        ThumbExample thumbExample = new ThumbExample();
        thumbExample.createCriteria().andLikerEqualTo(liker).andTypeEqualTo(type).andTargetIdEqualTo(targetId);
        return thumbMapper.selectByExample(thumbExample).size();
    }

    @Transactional
    public Object insert(ThumbDTO thumbDTO) {
        if (thumbDTO.getTargetId() == null || thumbDTO.getTargetId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (thumbDTO.getType() == null || !LikeTypeEnum.isExist(thumbDTO.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (thumbDTO.getType() == LikeTypeEnum.COMMENT.getType()) {
            // 点赞评论
            Comment dbComment = commentMapper.selectByPrimaryKey(thumbDTO.getTargetId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            ThumbExample thumbExample = new ThumbExample();
            thumbExample.createCriteria()
                    .andTargetIdEqualTo(thumbDTO.getTargetId())
                    .andTypeEqualTo(thumbDTO.getType())
                    .andLikerEqualTo(thumbDTO.getUser().getId());
            List<Thumb> thumbs = thumbMapper.selectByExample(thumbExample);
            if(thumbs.size()>=1) return ResultDTO.errorOf(CustomizeErrorCode.CAN_NOT_LIKE_AGAIN);
            Thumb thumb = new Thumb();
            BeanUtils.copyProperties(thumbDTO, thumb);
            thumb.setLiker(thumbDTO.getUser().getId());
            thumbMapper.insert(thumb);

            if(thumb.getType() == LikeTypeEnum.COMMENT.getType()){
                if(dbComment.getType()== CommentTypeEnum.QUESTION.getType()){//如果该评论是在问题下的
                    // 获取点赞的评论的问题（方便返回）
                    Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
                    if (question == null) {
                        throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                    }
                    // 增加点赞数
                    dbComment.setId(thumb.getTargetId());
                    dbComment.setLikeCount(1L);
                    thumbExtMapper.incLikeCount(dbComment);
                    // parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
                    // 创建通知
                    createNotify(thumb, dbComment.getCommentator(), thumbDTO.getUser().getName(), dbComment.getContent(), NotificationTypeEnum.LIKE_COMMENT, question.getId());
                }
            }

        }
        else  if (thumbDTO.getType() == LikeTypeEnum.QUESTION.getType()){
            //点赞问题
            Question question = questionMapper.selectByPrimaryKey(thumbDTO.getTargetId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            ThumbExample thumbExample = new ThumbExample();
            thumbExample.createCriteria()
                    .andTargetIdEqualTo(thumbDTO.getTargetId())
                    .andTypeEqualTo(thumbDTO.getType())
                    .andLikerEqualTo(thumbDTO.getUser().getId());
            List<Thumb> thumbs = thumbMapper.selectByExample(thumbExample);
            if(thumbs.size()>=1) return ResultDTO.errorOf(CustomizeErrorCode.CAN_NOT_LIKE_QUESTION_AGAIN);
            Thumb thumb = new Thumb();
            BeanUtils.copyProperties(thumbDTO, thumb);
            thumb.setLiker(thumbDTO.getUser().getId());
            thumbMapper.insert(thumb);
            // 增加点赞数
            question.setId(thumb.getTargetId());
            question.setLikeCount(1);
            thumbExtMapper.incQuestionLikeCount(question);
            // 创建通知
            createNotify(thumb, question.getCreator(), thumbDTO.getUser().getName(), question.getTitle(), NotificationTypeEnum.LIKE_QUESTION, question.getId());
        }
        return ResultDTO.okOf("点赞/收藏成功！");
    }

    private void createNotify(Thumb thumb, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver == thumb.getLiker()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(thumb.getLiker());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public Object remove(ThumbDTO thumbDTO) {
        ThumbExample thumbExample = new ThumbExample();
        thumbExample.createCriteria().andLikerEqualTo(thumbDTO.getUser().getId()).andTypeEqualTo(thumbDTO.getType()).andTargetIdEqualTo(thumbDTO.getTargetId());
        int c = thumbMapper.deleteByExample(thumbExample);
        if(c==0) {
            return ResultDTO.errorOf("哎呀，该收藏已移除或您无权移除！");
        }
        else {
            return ResultDTO.okOf("恭喜您，成功移除"+c+"条收藏！");
        }
    }
}
