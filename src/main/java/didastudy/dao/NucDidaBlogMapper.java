package didastudy.dao;

import didastudy.entity.NucDidaBlog;
import java.util.List;

public interface NucDidaBlogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaBlog record);

    NucDidaBlog selectById(Long id);

    NucDidaBlog selectByUserId(Long userId);

    NucDidaBlog selectByTitle(String title);

    List<NucDidaBlog> selectAll();

    int updateByPrimaryKey(NucDidaBlog record);
}