package didastudy.service;

import didastudy.dao.NucDidaRoleMapper;
import didastudy.entity.NucDidaRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleService {

    @Autowired
    NucDidaRoleMapper role;

    public List<NucDidaRole> getRoleByNumber(String number) {
        return role.selectByNumber(number);
    }
}
