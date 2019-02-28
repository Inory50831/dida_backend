package didastudy.service;

import didastudy.dao.NucDidaBlogMapper;
import didastudy.entity.NucDidaBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysBlogService {

    @Autowired
    private NucDidaBlogMapper blog;

    /**
     * 查询博文列表
     * @return a List
     */
    @Transactional(readOnly = true)
    public List<NucDidaBlog> listBlog() {
        return blog.selectAll();
    }

    /**
     * 查询单个博文
     * @param id
     * @return a BlogEntity
     */
    public NucDidaBlog getBlogById(Long id) {
        return blog.selectById(id);
    }

    /**
     * 查询单个博文
     * @param userId
     * @return a BlogEntity
     */
    public NucDidaBlog getBlogByUserId(Long userId) {
        return blog.selectByUserId(userId);
    }

    /**
     * 查询单个博文
     * @param title
     * @return a BlogEntity
     */
    public NucDidaBlog getBlogByTitle(String title) {
        return blog.selectByTitle(title);
    }

    /**
     * 修改博文信息
     * @param didaBlog
     * @return a BlogEntity
     */
    @Transactional
    public int updateBlog(NucDidaBlog didaBlog) {
        return blog.updateByPrimaryKey(didaBlog);
    }
}
