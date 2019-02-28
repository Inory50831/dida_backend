package didastudy.dao;

import didastudy.entity.NucDidaPost;
import java.util.List;

public interface NucDidaPostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaPost record);

    NucDidaPost selectById(Long id);

    NucDidaPost selectByUserId(Long userId);

    NucDidaPost selectByTitle(String title);

    List<NucDidaPost> selectAll();

    int updateByPrimaryKey(NucDidaPost record);
}