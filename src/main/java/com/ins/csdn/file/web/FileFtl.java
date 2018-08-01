package com.ins.csdn.file.web;

import com.ins.csdn.file.domain.FileInfoEntity;
import com.ins.csdn.file.domain.FileRepository;
import com.ins.csdn.file.domain.QFileInfoEntity;
import com.ins.csdn.tool.PageParam;
import com.ins.csdn.tool.Result;
import com.ins.csdn.tool.StringUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class FileFtl {


    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileRepository fileRepository;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    private FileController fileController;

    private String Sql(String src) {
        return "%"+(StringUtil.checkNull(src)?"%":src)+"%";
    }

    private Long Sql(Long src){return null==src?0:src;}

    public boolean getFile (String id) {
        if(StringUtil.checkNull(id)) {
            return false;
        }
        try{
            QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
            FileInfoEntity fileInfoEntity = new FileInfoEntity();
            fileInfoEntity.setFileId(id);
            List<FileInfoEntity> entities = queryFactory.select(qFileInfoEntity)
                    .from(qFileInfoEntity)
                    .where(qFileInfoEntity.fileId.eq(id)).fetch();
            queryFactory.update(qFileInfoEntity)
                    .set(qFileInfoEntity.times,(null==entities.get(0).getTimes()?0:entities.get(0).getTimes())+1)
                    .where(qFileInfoEntity.fileId.eq(fileInfoEntity.getFileId())).execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/index")
    public ModelAndView init(ModelAndView modelAndView,String fileName, String fileURL, String suffix,Integer pageIndex,Integer pageSize){
        PageParam pageParam = new PageParam();
        pageParam.setPageIndex(null==pageIndex?0:pageIndex);
        pageParam.setPageSize(20);
        Result result = this.fileController.selectFile(fileName,fileURL,suffix,pageParam);
        JSONObject object = JSONObject.fromObject(result.getData());
        JSONArray array = JSONArray.fromObject(object.getJSONArray("content"));
        JSONArray array1 = new JSONArray();
        array.forEach(m ->{
            JSONObject object1 = JSONObject.fromObject(m);
            Long temp = object1.getLong("downloadTime");
            object1.remove("downloadTime");
            object1.put("downloadTime",new Date(temp));
            Long temp2 = object1.getLong("uploadTime");
            object1.remove("uploadTime");
            object1.put("uploadTime",new Date(temp2));
            System.out.print(object1);
            array1.add(object1);
        });
        modelAndView.addObject("data",array1);
        modelAndView.addObject("pageParam",JSONObject.fromObject(result.getPageParam()));
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(ModelAndView modelAndView,String fileName, String fileURL, String suffix,Integer pageIndex,Integer pageSize){
        PageParam pageParam = new PageParam();
        pageParam.setPageIndex(null==pageIndex?0:pageIndex);
        pageParam.setPageSize(20);
        Result result = this.fileController.selectFile(fileName,fileURL,suffix,pageParam);
        JSONObject object = JSONObject.fromObject(result.getData());
        JSONArray array = JSONArray.fromObject(object.getJSONArray("content"));
        JSONArray array1 = new JSONArray();
        array.forEach(m ->{
            JSONObject object1 = JSONObject.fromObject(m);
            Long temp = object1.getLong("downloadTime");
            object1.remove("downloadTime");
            object1.put("downloadTime",new Date(temp));
            Long temp2 = object1.getLong("uploadTime");
            object1.remove("uploadTime");
            object1.put("uploadTime",new Date(temp2));
            System.out.print(object1);
            array1.add(object1);
        });
        modelAndView.addObject("data",array1);
        modelAndView.addObject("pageParam",JSONObject.fromObject(result.getPageParam()));
        return modelAndView;
    }

    @RequestMapping("list")
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.addObject("page",0);
        return modelAndView;
    }

    @RequestMapping("test")
    public ModelAndView test(ModelAndView modelAndView) {
        modelAndView.addObject("page",0);
        return modelAndView;
    }
}
