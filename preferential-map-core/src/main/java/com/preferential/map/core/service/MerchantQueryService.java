/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-12 21:28
 **/

package com.preferential.map.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.preferential.map.common.domain.MerchantRequest;
import com.preferential.map.common.exception.PmapException;
import com.preferential.map.core.domain.MerchantDTO;
import com.preferential.map.core.domain.MerchantInfoDTO;
import com.preferential.map.core.domain.MerchantInfoDTO.MerchantInfoDTOBuilder;
import com.preferential.map.dal.domain.Merchant;
import com.preferential.map.dal.domain.MerchantInfo;
import com.preferential.map.dal.domain.UserInfo;
import com.preferential.map.dal.mapper.MerchantInfoMapper;
import com.preferential.map.dal.mapper.MerchantMapper;
import com.preferential.map.dal.service.MerchantService;
import com.preferential.map.dal.service.UserInfoService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class MerchantQueryService {

    public static final String DESCRIPTION = "description";
    public static final String QUERYBYNAME_MERCHANTREQUEST = "QUERYBYNAME-MERCHANTREQUEST";
    @Resource
    MerchantService merchantService;

    @Resource
    MerchantInfoMapper merchantInfoMapper;

    @Resource
    MerchantMapper merchantMapper;

    @Resource
    UserInfoService userInfoService;
    private final GeometryFactory factory = new GeometryFactory();


    /**
     * 根据位置查询
     *
     * @param request
     * @return
     */
    public List<MerchantDTO> queryByLocation(MerchantRequest request) {
        String location = request.getLocation();
        if (StringUtils.isEmpty(location) || request.getLevel() == null) {
            throw new PmapException("缺少必要参数");
        }
        double level = request.getLevel();
        String[] coors = StringUtils.split(location, ",");
        if (coors.length != 2) {
            throw new PmapException("坐标格式不正确");
        }

        double x = Double.parseDouble(coors[1].trim());
        double y = Double.parseDouble(coors[0].trim());
        //[19级，18级，17级，16级，15级，14级，13级，12级]
        //20米, 50米，100米，200米，500米，1公里，2公里，5公里]
        //小于10级不展示,12级时大概一个手机幅面25*25公里为一个城市的大小
        if (level < 10) {
            return new ArrayList<>();
        }
        //计算大概的经纬度距离
        double distance = 10 * 1000 / 26.5 / 3600 / Math.pow(2, level - 12);
        Coordinate coordinate0 = new Coordinate(x - distance, y - distance);
        Coordinate coordinate1 = new Coordinate(x - distance, y + distance);
        Coordinate coordinate2 = new Coordinate(x + distance, y + distance);
        Coordinate coordinate3 = new Coordinate(x + distance, y - distance);
        Coordinate[] coorArray = new Coordinate[]{coordinate0, coordinate1, coordinate2, coordinate3, coordinate0};
        //查询范围
        Polygon buffer = factory.createPolygon(coorArray);
        //商户类型
        Integer merchantType = request.getMerchantType() == null ? 0 : request.getMerchantType();

        //获取用户配置
        UserInfo userInfo = userInfoService.getById(request.getUserId());
        //持证类型
        Integer credentialType = userInfo.getCredentialType();
        String key = UserOperateService.MERCHANT_AREA;
        JSONArray merchantArea = UserOperateService.defaultConfig.getJSONArray(key);
        if (userInfo != null && userInfo.getConfig() != null) {
            merchantArea = userInfo.getConfig().getJSONArray(key);
        }
        //商家优待区域
        List<Integer> area = merchantArea.toJavaList(Integer.class);

        List<Merchant> merchants = merchantMapper.queryByMerchantLocationAndMerchantType(buffer.toText(), merchantType,
                credentialType, area);

        return merchants.parallelStream().map(it -> convertMerchantDTO(it, true)).collect(Collectors.toList());

    }

    /**
     * 根据名称查询
     *
     * @param request
     * @return
     */
    @Cacheable(value = QUERYBYNAME_MERCHANTREQUEST)
    public List<MerchantDTO> queryByName(MerchantRequest request) {
        String input = request.getInput();
        if (StringUtils.isEmpty(input) || input.length() < 2) {
            return new ArrayList<>();
        }

        List<Merchant> merchants = merchantMapper.queryByMerchantName("+" + request.getInput());
        return merchants.parallelStream().map(it -> convertMerchantDTO(it, false)).collect(Collectors.toList());


    }

    /**
     * 根据位置查询
     *
     * @param request
     * @return
     */
    public MerchantInfoDTO queryById(MerchantRequest request) {
        Long merchantId = request.getMerchantId();
        if (merchantId == null) {
            throw new PmapException("商户ID必传");
        }
        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null) {
            throw new PmapException("找不到商户");
        }
        List<MerchantInfo> merchantInfos = merchantInfoMapper.queryByMerchantId(merchantId);
        if (CollectionUtils.isEmpty(merchantInfos)) {
            throw new PmapException("找不到商户信息");
        }
        MerchantInfo merchantInfo = merchantInfos.get(0);
        return convertMerchantInfoDTO(merchant, merchantInfo);

    }

    private static MerchantInfoDTO convertMerchantInfoDTO(Merchant merchant, MerchantInfo merchantInfo) {
        MerchantInfoDTOBuilder builder = MerchantInfoDTO.builder();
        JSONObject config = merchantInfo.getConfig();
        String description = config == null ? "" : config.getString(DESCRIPTION);
        description = description.replace("<repn>", "\\n");
        Point point = merchant.getMerchantLocation();
        String location = point.getX() + "," + point.getY();
        String profile = merchant.getProfile();
        profile = StringUtils.isEmpty(profile) ? "https://yd-map-1255426777.cos.ap-shanghai.myqcloud.com/merchant.jpg"
                : profile;

        JSONArray images = merchant.getImages();
        if (images == null || images.isEmpty()) {
            images = JSON.parseArray("[\"https://yd-map-1255426777.cos.ap-shanghai.myqcloud.com/merchant_img.jpg\"]");
        }
        return builder.merchantName(merchant.getMerchantName()).merchantType(merchant.getMerchantType())
                .merchantAddress(merchant.getMerchantAddress()).merchantId(merchant.getMerchantId()).images(images)
                .profile(profile).description(description).merchantLocation(location)
                .merchantArea(merchantInfo.getMerchantArea()).source(merchantInfo.getSource())
                .credentialType(merchantInfo.getCredentialType()).build();
    }

    private MerchantDTO convertMerchantDTO(Merchant merchant, boolean trim) {
        Point point = merchant.getMerchantLocation();
        String location = point.getX() + "," + point.getY();
        String merchantName = merchant.getMerchantName().trim().replace(" ", "");
        if (trim) {
            if (merchantName.length() > 10) {
                merchantName = merchantName.substring(0, 10) + "...";
            }
            if (merchantName.length() > 5) {
                merchantName = merchantName.substring(0, 5) + "\\n" + merchantName.substring(5);
            }
        }
        return MerchantDTO.builder().merchantLocation(location).merchantName(merchantName)
                .merchantId(merchant.getMerchantId()).merchantType(merchant.getMerchantType()).build();

    }

    public static void main(String[] args) {
        String merchantName = "123456789089";
        if (merchantName.length() > 10) {
            merchantName = merchantName.substring(0, 10) + "..";
        }
        if (merchantName.length() > 5) {
            merchantName = merchantName.substring(0, 5) + "\\n" + merchantName.substring(5);
        }
        System.out.println(merchantName);
    }


}
