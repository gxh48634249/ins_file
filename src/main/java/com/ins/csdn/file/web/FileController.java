package com.ins.csdn.file.web;

import com.ins.csdn.file.domain.FileInfoEntity;
import com.ins.csdn.file.domain.FileRepository;
import com.ins.csdn.file.domain.QFileInfoEntity;
import com.ins.csdn.tool.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping
@RestController
@Api(tags = "文件管理")
public class FileController {

    private JPAQueryFactory queryFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileRepository fileRepository;

    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }


    @Transactional
    @ApiOperation(value = "删除文件（包括源文件）",httpMethod = "POST")
    @RequestMapping("deleteFile")
    public Result deleteFile (String id) {
        try{
            QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
            queryFactory.delete(qFileInfoEntity)
                    .where(qFileInfoEntity.fileId.eq(MD5.encrypt(id))).execute();
            return new Result(ResultConstant.SUCCESS_STATU,"删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(ResultConstant.FAIL_STATU,"删除失败");
        }
    }

    @Transactional
    @ApiOperation(value = "修改文件信息",httpMethod = "POST")
    @RequestMapping("modifyFile")
    public Result modifyFile (FileInfoEntity fileInfoEntity) {
        String id = fileInfoEntity.getFileId();
        if(StringUtil.checkNull(id)) {
            return new Result(ResultConstant.FAIL_STATU,"请指定文件");
        }
        try{
            QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
            queryFactory.update(qFileInfoEntity)
                    .set(qFileInfoEntity.belongWebsite,fileInfoEntity.getBelongWebsite())
                    .set(qFileInfoEntity.fileAuthor,fileInfoEntity.getFileAuthor())
                    .set(qFileInfoEntity.downloadTime,fileInfoEntity.getDownloadTime())
                    .set(qFileInfoEntity.fileName,fileInfoEntity.getFileName())
                    .set(qFileInfoEntity.uploadTime,fileInfoEntity.getUploadTime())
                    .where(qFileInfoEntity.fileId.eq(fileInfoEntity.getFileId())).execute();
            return new Result(ResultConstant.SUCCESS_STATU,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(ResultConstant.FAIL_STATU,"修改失败");
        }
    }

    @Transactional
    @ApiOperation(value = "查询文件信息",httpMethod = "POST")
    @RequestMapping("selectFile")
    public Result selectFile (String fileName, String fileURL, String suffix, PageParam pageParam) {
        QFileInfoEntity qFileInfoEntity = QFileInfoEntity.fileInfoEntity;
        Predicate predicate = qFileInfoEntity.fileUrl.like(Sql(fileURL))
                .and(qFileInfoEntity.fileName.like(Sql(fileName)))
                .and(qFileInfoEntity.fileSuffix.like(Sql(suffix)));
        Sort sort = new Sort(Sort.Direction.ASC,"uploadTime");
        try{
            if(null!=pageParam&&null!=pageParam.getPageIndex()&&null!=pageParam.getPageSize()) {
                PageRequest request = PageRequest.of(pageParam.getPageIndex(),pageParam.getPageSize(),sort);
                Page<FileInfoEntity> page = this.fileRepository.findAll(predicate,request);
                pageParam.setTotleInfo(page.getTotalElements());
                pageParam.setTotlePage(page.getTotalPages());
                System.out.print(pageParam);
                Result result = new Result(ResultConstant.SUCCESS_STATU,"查询成功",page);
                result.setPageParam(pageParam);
                return  result;
            }else return new Result(ResultConstant.SUCCESS_STATU,"修改成功",this.fileRepository.findAll(predicate,sort));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(ResultConstant.FAIL_STATU,"修改失败");
        }
    }

    private String Sql(String src) {
        String result = "%"+((StringUtil.checkNull(src)||src.equals("undefined")||src.equals("ALL"))?"%":src)+"%";
        System.out.print(result);
        return result;
    }

    private Long Sql(Long src){return null==src?0:src;}

    @Transactional
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


}
