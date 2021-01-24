package cn.ght.demo.service;

import cn.ght.demo.dto.HistDataDto;
import cn.ght.demo.dto.HistoryDto;
import cn.ght.demo.dto.VersionDto;
import cn.ght.demo.entity.File;
import cn.ght.demo.entity.HandleEditModel;
import cn.ght.demo.entity.HistData;
import cn.ght.demo.entity.User;
import cn.ght.demo.mapper.FileMapper;
import cn.ght.demo.mapper.HistDataMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: ght
 * @Date: 2021/1/19
 */
@Service("editorService")
public class EditorService {
    @Resource
    private FileMapper fileMapper;

    @Resource
    private HistDataMapper histDataMapper;

    public HandleEditModel getEditMsg(Long id) {
        HandleEditModel model = new HandleEditModel();
        model.setCallbackUrl("http://192.168.31.100:9520/test/save?fileId=");
        File file = this.fileMapper.selectById(id);
        if (file.getHistStatus() == 0) {
            List<HistData> histList = this.histDataMapper.selectList(new QueryWrapper<HistData>().eq("file_id", file.getFileId()));
            Map<String, HistDataDto> dtos = handleHistData(histList, file);
            HistoryDto historyDto = handleHistory(histList);
            model.setHistData(dtos);
            model.setHistory(historyDto);
        }
        return model;
    }

    private Map<String, HistDataDto> handleHistData(List<HistData> histData, File file) {
        Map<Integer, HistData> map = new HashMap<>();
        for (HistData data : histData) {
            map.put(data.getVersion(), data);
        }
        Map<String, HistDataDto> result = new HashMap<>();
        for (int i = 0; i < histData.size() - 1; i++) {
            HistData data = histData.get(i);
            HistDataDto dto = new HistDataDto();
            dto.setChangesUrl(data.getChangesUrl());
            dto.setVersion(data.getVersion());
            dto.setKey(histData.get(i + 1).getPreKey());
            dto.setUrl(histData.get(i + 1).getPreUrl());
            HistDataDto previous = new HistDataDto();
            previous.setUrl(data.getPreUrl());
            previous.setKey(data.getPreKey());
            dto.setPrevious(previous);
            result.put(String.valueOf(i), dto);
        }
        Integer max = histData.stream().map(HistData::getVersion).max(Integer::compareTo).orElse(1);
        HistDataDto dataDto = new HistDataDto();
        dataDto.setVersion(max);
        HistDataDto previous = new HistDataDto();
        previous.setKey(map.get(max).getPreKey());
        previous.setUrl(map.get(max).getPreUrl());
        dataDto.setPrevious(previous);
        dataDto.setKey(file.getFileKey());
        dataDto.setChangesUrl(map.get(max).getChangesUrl());
        dataDto.setUrl(file.getUrl());
        result.put(String.valueOf(max),dataDto);
        return result;
    }

    private HistoryDto handleHistory(List<HistData> histData) {
        HistoryDto result = new HistoryDto();
        List<VersionDto> history = new ArrayList<>();
        for (HistData data : histData) {
            VersionDto dto = new VersionDto();
            dto.setCreated("");
            dto.setVersion(data.getVersion());
            dto.setKey(data.getFileKey());
            dto.setServerVersion("6.1.0");
            // TODO: 2021/1/20 查询修改人的信息
            List<VersionDto> list = new ArrayList<>();
            VersionDto versionDto = new VersionDto();
            User user = new User();
            versionDto.setUser(user);
            versionDto.setCreated("");
            list.add(versionDto);
            dto.setChanges(list);
            history.add(dto);
        }
        Integer max = histData.stream().map(HistData::getVersion).max(Integer::compareTo).orElse(1);
        result.setHistory(history);
        result.setCurrentVersion(max);
        return result;
    }
}
