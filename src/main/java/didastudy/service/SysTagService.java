package didastudy.service;

import didastudy.dao.NucDidaTagMapper;
import didastudy.entity.NucDidaTag;
import didastudy.entity.NucDidaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysTagService {

    @Autowired
    private NucDidaTagMapper tag;

    /**
     * 查询标签列表
     * @return a List
     */
    @Transactional(readOnly = true)
    public List<NucDidaTag> getAllTag() {
        return tag.selectAll();
    }

    /**
     * 查询标签信息
     * @param name
     * @return a TagEntity
     */
    public NucDidaTag getTagByName(String name) {
        return tag.selectByName(name);
    }

    /**
     * 查询标签信息
     * @param id
     * @return a TagEntity
     */
    public NucDidaTag getTagById(Long id) {
        return tag.selectById(id);
    }

    /**
     * 添加标签
     * @param didaTag
     * @return a TagEntity
     */
    @Transactional
    public int saveTag(NucDidaTag didaTag) {
        return tag.insert(didaTag);
    }

    /**
     * 修改标签
     * @param didaTag
     * @return a TagEntity
     */
    @Transactional
    public int updateTag(NucDidaTag didaTag) {
        return tag.updateByPrimaryKey(didaTag);
    }

    /**
     * 删除标签
     * @param id
     * @return a Status
     */
    public int deleteTag(Long id) {
        return tag.deleteByPrimaryKey(id);
    }

    /**
     * 用户是否重复
     * @param didaTag
     * @return
     */
    public boolean isTagExist(NucDidaTag didaTag) {
        return getTagByName(didaTag.getName()) != null;
    }
}
