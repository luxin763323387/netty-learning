package com.cn.lx.bugstack.udp.serializer.impl;

import com.cn.lx.bugstack.udp.protocol.VehicleStatusPacket;
import com.cn.lx.bugstack.udp.serializer.Serializer;

/**
 * @author StevenLu
 * @date 2023/3/3 3:50 下午
 */
public class VehicleStatusSerializerImpl implements Serializer {

    @Override
    public <T> T deserialize(Class<T> clazz, String msg) {
        VehicleStatusPacket vehicleStatusPacket = new VehicleStatusPacket();
        String[] fields = msg.split(",");
        vehicleStatusPacket.setTextID(fields[0]);
        vehicleStatusPacket.setMcpName(fields[1]);
        vehicleStatusPacket.setOhtID(fields[2]);
        vehicleStatusPacket.setStatus(fields[3]);
        vehicleStatusPacket.setIsUsed(fields[4]);
        vehicleStatusPacket.setErrorCode(fields[5]);
        vehicleStatusPacket.setCommuStatus(fields[6]);
        //如果数据的长度小于5位，则补0，大于不作处理
        if (fields[7].length() < 5) {
            vehicleStatusPacket.setCurLoc(getZero(fields[7].length(), fields[7]));
        } else {
            vehicleStatusPacket.setCurLoc(fields[7]);
        }
        vehicleStatusPacket.setInterval(fields[8]);
        //如果数据的长度小于5位，则补0，大于不作处理
        if (fields[9].length() < 5) {
            vehicleStatusPacket.setNextLoc(getZero(fields[9].length(), fields[9]));
        } else {
            vehicleStatusPacket.setNextLoc(fields[9]);
        }
        vehicleStatusPacket.setCycleID(fields[10]);
        vehicleStatusPacket.setCycleProcess(fields[11]);
        vehicleStatusPacket.setCastID(fields[12]);
        //如果数据的长度小于5位，则补0，大于不作处理
        if (fields[13].length() < 5) {
            vehicleStatusPacket.setDestination(getZero(fields[13].length(), fields[13]));
        } else {
            vehicleStatusPacket.setDestination(fields[13]);
        }
        vehicleStatusPacket.setEmStatus(fields[14]);
        return (T) vehicleStatusPacket;
    }

    public String getZero(int i, String value) {
        String returnValue = "";
        switch (i) {
            case 1:
                returnValue = "0000" + value;
                break;
            case 2:
                returnValue = "000" + value;
                break;
            case 3:
                returnValue = "00" + value;
                break;
            case 4:
                returnValue = "0" + value;
                break;
            default:
                returnValue = value;
                break;
        }
        return returnValue;
    }
}
