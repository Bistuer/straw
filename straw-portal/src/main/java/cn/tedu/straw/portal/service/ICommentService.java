package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 对解答的答案新增评论
     *
     * @param commentVo
     * @param username
     * @return
     */
    Comment saveComment(CommentVo commentVo, String username);

    /**
     * 删除评论
     *
     * @param commentId
     * @param username
     * @return
     */
    boolean removeComment(Integer commentId, String username);


    /**
     * 修改评论
     *
     * @param commentId
     * @param commentVo
     * @param username
     * @return
     */
    Comment updateComment(Integer commentId, CommentVo commentVo, String username);
}
