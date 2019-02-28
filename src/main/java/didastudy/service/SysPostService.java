package didastudy.service;

import didastudy.dao.NucDidaPostMapper;
import didastudy.entity.NucDidaPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysPostService {

    @Autowired
    private NucDidaPostMapper post;

    /**
     * 查询帖子列表
     * @return a List
     */
    @Transactional(readOnly = true)
    public List<NucDidaPost> listPost() {
        return post.selectAll();
    }

    /**
     * 查询帖子信息
     * @param id
     * @return a PostEntity
     */
    public NucDidaPost getPostById(Long id) {
        return post.selectById(id);
    }

    /**
     * 查询帖子信息
     * @param userId
     * @return a PostEntity
     */
    public NucDidaPost getPostByUserId(Long userId) {
        return post.selectByUserId(userId);
    }

    /**
     * 查询帖子信息
     * @param title
     * @return a PostEntity
     */
    public NucDidaPost getPostByTitle(String title) {
        return post.selectByTitle(title);
    }

    /**
     * 修改帖子信息
     * @param didaPost
     * @return a PostEntity
     */
    @Transactional
    public int updatePost(NucDidaPost didaPost) {
        return post.updateByPrimaryKey(didaPost);
    }
}
