package com.example.cinema_8k.fogocontroller;

import java.io.Serializable;

/**
 * Created by CINEMA-8K on 22/02/2016.
 */
public class Computer implements Serializable {
    private String name;
    private String ip;
    private String mac;
    private String magic_id;
    private boolean checked;

    public Computer(String name, String ip, String mac, String magic_id, boolean checked) {
        this.name = name;
        this.ip = ip;
        this.mac = mac;
        this.magic_id = magic_id;
        this.checked = checked;
    }

    public String getMac() {
        return mac;
    }

    public String getMagic_id() {
        return magic_id;
    }

    public Computer(String name, String ip, boolean checked) {
        this.name = name;
        this.ip = ip;
        this.checked = checked;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
