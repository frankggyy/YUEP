package com.yuep.nms.biz.sbi.eqm;

import com.yuep.nms.biz.common.sbi.EqmAdaptor;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

public class EqmAdaptorImpl implements EqmAdaptor {
    public String getNeUserLabel(MoNaming neNaming) {
        // NeLoginParameter ne =
        // SbiContext.getNeloginParameterByNeNaming(neNaming);
        // SnmpProtocalLoginParam loginParam =
        // SbiCommonUtil.getSnmpProtocalLoginParam(ne);
        // SnmpOperator operator = new SnmpOperator(loginParam);
        // SnmpVarBind var =
        // operator.getSingleValue(".1.3.6.1.4.1.27608.1.1.1.3.0");
        // return SnmpContentUtil.snmpVar2Str(var.getVariable());
        return "userLabel get in adaptor";
    }

    @Override
    public void updateNeUserLabel(MoNaming neNaming, String userLabel) {
        // NeLoginParameter ne =
        // SbiContext.getNeloginParameterByNeNaming(neNaming);
        // SnmpProtocalLoginParam loginParam =
        // SbiCommonUtil.getSnmpProtocalLoginParam(ne);
        // SnmpOperator operator = new SnmpOperator(loginParam);
        // operator.setSingleValue(".1.3.6.1.4.1.27608.1.1.1.3.0", new
        // SnmpString(userLabel));
    }
}
