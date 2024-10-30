package com.app.main.altibase.service.Implement;

import java.util.List;
import com.app.main.altibase.mapper.AltibaseMapper;
import com.app.main.altibase.service.AltibaseService;
import com.app.main.altibase.vo.AltibaseVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    //서비스 임을 명시
public class AltibaseServiceImpl implements AltibaseService {
    
    @Autowired
    private AltibaseMapper altibaseMapper;

    /**
     * 전체 리스트 조회
     */
    @Override
    public List<AltibaseVo> list() {
        return altibaseMapper.list();
    }

}
