package com.superb.upetstar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.superb.upetstar.common.pojo.entity.Image;
import com.superb.upetstar.mapper.ImageMapper;
import com.superb.upetstar.service.IImageService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hym
 * @since 2022-04-15
 */
@DubboService
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements IImageService {

}
