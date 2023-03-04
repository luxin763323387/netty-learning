package com.cn.lx.bugstack.udp.protocol;

import com.cn.lx.bugstack.udp.command.Command;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * 小车状态指令
 *
 * @author StevenLu
 * @date 2023/3/3 3:02 下午
 */
@Data
public class VehicleStatusPacket extends Packet {


    private Long time = System.currentTimeMillis();

    private String textID;

    private String mcpName;

    private String ohtID;

    private String status;

    private String isUsed;

    private String errorCode;

    private String commuStatus;

    private String curLoc;

    private String interval;

    private String nextLoc;

    private String cycleID;

    private String cycleProcess;

    private String castID;

    private String destination;

    private String emStatus;

    @Override
    public Byte getCommand() {
        return Command.STATUS;
    }
}
