package com.spk.web.myfirstws.service.impl;

import com.spk.web.myfirstws.service.AddressService;
import com.spk.web.myfirstws.shared.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public List<AddressDto> getAddresses(String userId) {
        return null;
    }
}
