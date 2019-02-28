package didastudy.dao;

import didastudy.entity.NucDidaPermission;
import java.util.List;

public interface NucDidaPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaPermission record);

    NucDidaPermission selectByPrimaryKey(Long id);

    List<NucDidaPermission> selectAll();

    int updateByPrimaryKey(NucDidaPermission record);
}