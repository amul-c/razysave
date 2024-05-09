package com.razysave.dto.device;

import lombok.Data;

import java.util.HashMap;
@Data
public class InstalledDevices {
    private HashMap<String,Integer> deviceCount;
}

