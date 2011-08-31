package com.yuep.nms.core.sbi.sbicore.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.yuep.base.exception.YuepException;
import com.yuep.base.resource.FileLoader;
import com.yuep.base.util.format.Pair;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.common.sbi.model.DeviceAdaptor;
import com.yuep.nms.core.common.sbi.util.SbiCommonUtil;
import com.yuep.nms.core.sbi.sbicore.SbiContext;

/**
 * <p>
 * Title: SbiAdaptorUtil
 * </p>
 * <p>
 * SBI侧处理Adaptor的工具类
 * </p>
 * 
 * @author pl
 * created 2011-4-14 下午03:34:48
 * modified [who date description]
 * check [who date description]
 */
public class SbiAdaptorUtil {
    
    /**
     * 解析appctx-sbiadaptor.xml,配置信息会转换为DeviceAdaptor
     * @param path appctx-sbiadaptor.xml
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<DeviceAdaptor> parseAdaptorXml(String path) {
        List<DeviceAdaptor> adaptors = new ArrayList<DeviceAdaptor>();
        SAXBuilder builder = new SAXBuilder(false);
        try {
            InputStream inputStream = FileLoader.getInputStream(path);
            Document doc = builder.build(inputStream);
            Element books = doc.getRootElement();
            List adaptorList = books.getChildren("Adaptor");
            for (Iterator iter = adaptorList.iterator(); iter.hasNext();) {
                Element adaptor = (Element) iter.next();
                String deviceType = adaptor.getAttributeValue("deviceType");
                String version = adaptor.getAttributeValue("version");
                DeviceAdaptor deviceAdaptor = new DeviceAdaptor();
                deviceAdaptor.setDeviceType(deviceType);
                deviceAdaptor.setVersion(version);
                List serviceList = adaptor.getChildren("prop");
                for (Object service : serviceList) {
                    Element serviceElement = (Element) service;
                    String type = serviceElement.getAttributeValue("type");
                    String implClass = serviceElement.getAttributeValue("implClass");
                    String intfClass = serviceElement.getAttributeValue("intfClass");
                    deviceAdaptor.getSeviceMap().put(type, new Pair<String, String>(implClass, intfClass));
                }
                System.out.println(deviceAdaptor);
                adaptors.add(deviceAdaptor);
            }
        } catch (Exception e) {
            throw new YuepException(e);
        }
        return adaptors;
    }

    /**
     * 通过service name得到service类
     * @param name
     * @return
     */
    public Class<?> getServiceClassByName(String name) {
        List<DeviceAdaptor> adaptorList = SbiContext.deviceAdaptorList;
        for (DeviceAdaptor adaptor : adaptorList) {
            String deviceType = adaptor.getDeviceType();
            String version = adaptor.getVersion();
            Map<String, Pair<String, String>> serviceMap = adaptor.getSeviceMap();
            for(Map.Entry<String, Pair<String, String>> entry:serviceMap.entrySet()){
                String serviceName = SbiCommonUtil.getAdaptorServiceName(deviceType,version,entry.getKey());
                String intf = entry.getValue().getV();
                if (serviceName.equals(name))
                    try {
                        return Thread.currentThread().getContextClassLoader().loadClass(intf);
                    } catch (Exception e) {
                        throw new YuepException(e);
                    }
            }
        }
        return null;
    }

    /**
     * 通过adaptor信息来创建adaptor，并注册为local service
     * @param adaptors
     */
    @SuppressWarnings("unchecked")
    public void rigisterServiceByAdaptor(List<DeviceAdaptor> adaptors) {
        for (DeviceAdaptor adaptor : adaptors) {
            String deviceType = adaptor.getDeviceType();
            String version = adaptor.getVersion();
            Map<String, Pair<String, String>> serviceMap = adaptor.getSeviceMap();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            for(Map.Entry<String, Pair<String, String>> entry:serviceMap.entrySet()){
                // 组装出一个本地服务名
                String serviceName = SbiCommonUtil.getAdaptorServiceName(deviceType,version,entry.getKey());
                Pair<String, String> pair = entry.getValue();
                String impl = pair.getK();
                String intf = pair.getV();
                // 构造adaptor,并注册为local service
                try {
                    Class implClass = classLoader.loadClass(impl);
                    Class intfClass =  classLoader.loadClass(intf);
                    CoreContext.getInstance().setLocalService(serviceName, intfClass, implClass.newInstance());
                } catch (Exception e) {
                    throw new YuepException(e);
                }
            }

        }
    }
    
}
