package didastudy.dao;

import didastudy.entity.NucDidaTag;
import java.util.List;

public interface NucDidaTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaTag record);

    NucDidaTag selectById(Long id);

    NucDidaTag selectByName(String name);

    List<NucDidaTag> selectAll();

    int updateByPrimaryKey(NucDidaTag record);
}