package com.spk.web.myfirstws.service;

import com.spk.web.myfirstws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> getAddresses(String userId);
}
