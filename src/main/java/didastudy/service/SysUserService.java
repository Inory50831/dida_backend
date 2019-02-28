package didastudy.service;

import didastudy.dao.NucDidaUserMapper;
import didastudy.entity.NucDidaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SysUserService {

    @Autowired
    private NucDidaUserMapper user;

    /**
     * 查询用户列表
     * @return a List
     */
    @Transactional(readOnly = true)
    public List<NucDidaUser> listUser() {
        return user.selectAll();
    }

    /**
     * 查询单个用户
     * @param number
     * @return a userEntity
     */
    public NucDidaUser getUserByNumber(String number) {
        return user.selectByNumber(number);
    }

    /**
     * 查询单个用户
     * @param id
     * @return a userEntity
     */
    public NucDidaUser getUserById(Long id) {
        return user.selectById(id);
    }

    /**
     * 添加用户
     * @param didaUser
     * @return a Status
     */
    @Transactional
    public int saveUser(NucDidaUser didaUser) {
        return user.insert(didaUser);
    }

    /**
     * 修改用户
     * @param didaUser
     * @return a Status
     */
    @Transactional
    public int updateUser(NucDidaUser didaUser) {
        return user.updateByPrimaryKey(didaUser);
    }

    /**
     * 用户是否重复
     * @param didaUser
     * @return
     */
    public boolean isUserExist(NucDidaUser didaUser) {
        return getUserByNumber(didaUser.getNumber()) != null;
    }

}
