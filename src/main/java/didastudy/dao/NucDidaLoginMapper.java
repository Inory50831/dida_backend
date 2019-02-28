package didastudy.dao;

import didastudy.entity.NucDidaLogin;
import java.util.List;

public interface NucDidaLoginMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NucDidaLogin record);

    NucDidaLogin selectByPrimaryKey(Long id);

    NucDidaLogin selectByNumber(String number);

    List<NucDidaLogin> selectAll();

    int updateByPrimaryKey(NucDidaLogin record);
}