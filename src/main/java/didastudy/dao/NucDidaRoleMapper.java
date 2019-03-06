package didastudy.dao;

import didastudy.entity.NucDidaRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NucDidaRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaRole record);

    NucDidaRole selectByPrimaryKey(Long id);

    List<NucDidaRole> selectByNumber(@Param("number") String number);

    List<NucDidaRole> selectAll();

    int updateByPrimaryKey(NucDidaRole record);
}