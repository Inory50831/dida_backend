package didastudy.service;

import didastudy.dao.NucDidaLoginMapper;
import didastudy.entity.NucDidaLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    @Autowired
    private NucDidaLoginMapper login;

    public NucDidaLogin getUserByNumber(String number) {
        return login.selectByNumber(number);
    }
}
