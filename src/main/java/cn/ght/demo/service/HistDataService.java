package cn.ght.demo.service;

import cn.ght.demo.entity.HistData;
import cn.ght.demo.mapper.HistDataMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: ght
 * @Date: 2021/1/20
 */
@Service("histDataService")
public class HistDataService extends ServiceImpl<HistDataMapper, HistData> {
}
