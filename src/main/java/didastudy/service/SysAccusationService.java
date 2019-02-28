package didastudy.service;

import didastudy.dao.NucDidaAccusationMapper;
import didastudy.entity.NucDidaAccusation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAccusationService {

    @Autowired
    private NucDidaAccusationMapper accusation;

    /**
     * 查询举报列表
     * @return a List
     */
    public List<NucDidaAccusation> listAccusation() {
        return accusation.selectAll();
    }

    /**
     * 查询举报信息
     * @param id
     * @return a AccusationEntity
     */
    public NucDidaAccusation getAccusationById(Long id) {
        return accusation.selectByPrimaryKey(id);
    }

    /**
     * 删除举报信息
     * @param id
     * @return a Status
     */
    public int deleteAccusation(Long id) {
        return accusation.deleteByPrimaryKey(id);
    }
}
