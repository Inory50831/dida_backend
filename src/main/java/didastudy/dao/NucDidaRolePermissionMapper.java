package didastudy.dao;

import didastudy.entity.NucDidaRolePermission;
import java.util.List;

public interface NucDidaRolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaRolePermission record);

    NucDidaRolePermission selectByPrimaryKey(Long id);

    List<NucDidaRolePermission> selectAll();

    int updateByPrimaryKey(NucDidaRolePermission record);
}