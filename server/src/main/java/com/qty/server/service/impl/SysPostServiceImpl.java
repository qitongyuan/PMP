package com.qty.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qty.common.response.StatusCode;
import com.qty.common.utils.PageUtil;
import com.qty.common.utils.QueryUtil;
import com.qty.model.entity.SysPostEntity;
import com.qty.model.mapper.SysPostDao;
import com.qty.server.service.SysPostService;
import com.qty.server.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements SysPostService {

    private static final Logger log= LoggerFactory.getLogger(SysPostServiceImpl.class);

    /**
     * 分页模糊检索
     * @param params
     * @return
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        //拿到检索条件
        String search=(params.get("search")==null)?"":params.get("search").toString();
        //调用自封装的分页查询工具(一页多少，升序降序、当前第几页)返回成Ipage
        IPage<SysPostEntity>queryPage=new QueryUtil<SysPostEntity>().getQueryPage(params);
        //写查询的Wrapper
        QueryWrapper wrapper=new QueryWrapper<SysPostEntity>()
                .like(StringUtils.isNotBlank(search),"post_code",search.trim())
                .or(StringUtils.isNotBlank(search))
                .like(StringUtils.isNotBlank(search),"post_name",search.trim());
        IPage<SysPostEntity> resPage=this.page(queryPage,wrapper);
        //返回结果重载了泛型
        return new PageUtil(resPage);
    }

    /**
     * 新增岗位
     * @param entity
     */
    @Override
    public void savePost(SysPostEntity entity) {
        if (this.getOne(new QueryWrapper<SysPostEntity>().eq("post_code",entity.getPostCode()))!=null){
            throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
        }
        entity.setCreateTime(DateTime.now().toDate());
        save(entity);
    }
}
