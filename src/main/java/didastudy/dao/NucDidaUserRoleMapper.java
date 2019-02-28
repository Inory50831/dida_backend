package didastudy.dao;

import didastudy.entity.NucDidaUserRole;

import java.util.List;

public interface NucDidaUserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaUserRole record);

    NucDidaUserRole selectByPrimaryKey(Long id);

    List<NucDidaUserRole> selectAll();

    int updateByPrimaryKey(NucDidaUserRole record);
}