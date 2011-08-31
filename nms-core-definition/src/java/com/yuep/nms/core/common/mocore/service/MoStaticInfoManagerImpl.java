/*
 * $Id: MoStaticInfoManagerImpl.java, 2011-3-29 下午01:11:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.resource.FileLoader;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.base.xml.ArrayListItems;
import com.yuep.base.xml.XmlFileReader;
import com.yuep.nms.core.common.mocore.model.MoStaticInfo;
import com.yuep.nms.core.common.mocore.model.SubMoInfo;

/**
 * <p>
 * Title: MoStaticInfoManagerImpl
 * </p>
 * <p>
 * Description:
 * MoStaticInfoManager接口实现类
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 下午01:11:31
 * modified [who date description]
 * check [who date description]
 */
public class MoStaticInfoManagerImpl implements MoStaticInfoManager {

    public Map<String,MoStaticInfo> moStaticInfos=new HashMap<String,MoStaticInfo>();
    
    private String[] moStaticInfoFiles;
    public static final String MO_MODEL_INFO_MAPPING_FILE="mo_static_info_mapping.xml";
    
    public MoStaticInfoManagerImpl(String... moStaticInfoFiles){
        this.moStaticInfoFiles=moStaticInfoFiles;
    }
    
    
    @SuppressWarnings("unchecked")
    public void init(){
        for(String moStaticInfoFile:moStaticInfoFiles){
            InputStream mappingInputStream=this.getClass().getResourceAsStream(MO_MODEL_INFO_MAPPING_FILE);
            InputStream dataInputStream=FileLoader.getInputStream(moStaticInfoFile);
            ArrayListItems<MoStaticInfo> data= (ArrayListItems<MoStaticInfo>)XmlFileReader.getXmlConfig(mappingInputStream, dataInputStream);
            if(CollectionUtils.isNotEmpty(data.getItems())){
                for(MoStaticInfo moStaticInfo:data.getItems()){
                    moStaticInfos.put(moStaticInfo.getType(), moStaticInfo);
                }
            }
        }
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.staticinfo.service.MoStaticInfoManager#getMoStaticInfo(java.lang.String)
     */
    @Override
    public MoStaticInfo getMoStaticInfo(String type) {
        return moStaticInfos.get(type);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoStaticInfoManager#accept(java.lang.String, java.lang.String)
     */
    @Override
    public boolean accept(String parentType, String childType) {
        MoStaticInfo parentMoStaticInfo=this.getMoStaticInfo(parentType);
        MoStaticInfo childMoStaticInfo=this.getMoStaticInfo(childType);
        for(SubMoInfo subMoInfo:parentMoStaticInfo.getSubMoInfos()){
            if(subMoInfo.getAcceptedTypes()!=null&&subMoInfo.getAcceptedTypes().contains(childType))
                return true;
            if(subMoInfo.getAcceptedSubKinds()!=null&&subMoInfo.getAcceptedSubKinds().contains(childMoStaticInfo.getSubkind()))
                return true;
            if(subMoInfo.getAcceptedKinds()!=null&&subMoInfo.getAcceptedKinds().contains(childMoStaticInfo.getKind()))
                return true;
          
        }
        return false;
    }


    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.service.MoStaticInfoManager#contain(java.lang.String, java.lang.String[])
     */
    @Override
    public boolean contain(String type, String... subKinds) {
        List<String>subKindList=YuepObjectUtils.newArrayList(subKinds);
        MoStaticInfo moStaticInfo=this.getMoStaticInfo(type);
        String subKind=moStaticInfo.getSubkind();
        return subKindList.contains(subKind);
    }

}
