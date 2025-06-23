package com.yawarSoft.Modules.Admin.Services.Interfaces;

import com.yawarSoft.Core.Dto.ApiResponse;
import com.yawarSoft.Modules.Admin.Dto.BloodBankDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankListDTO;
import com.yawarSoft.Modules.Admin.Dto.BloodBankSelectOptionDTO;
import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Admin.Dto.Reponse.BloodBankOptionsAddNetworkDTO;
import com.yawarSoft.Modules.Admin.Dto.UserListDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BloodBankService {

    Page<BloodBankListDTO> getBloodBankPaginated(int page, int size, String name, String region,
                                                 String province, String district, Boolean isInternal);
    BloodBankDTO getBloodBankById(int id);
    BloodBankDTO updateBloodBank(int id, BloodBankDTO bloodBankDTO);
    BloodBankDTO createBloodBank(BloodBankDTO bloodBankDTO);
    BloodBankDTO changeStatus(Integer id);

    List<BloodBankSelectOptionDTO> getBloodBankSelectorInternal();
    List<BloodBankSelectOptionDTO> getBloodBankSelectorAll();
    Optional<BloodBankEntity> getBloodBankEntityById(Integer id);

    String updateBloodBankProfileImage(Integer bloodBankId, MultipartFile profileImage) throws IOException;
    ResponseEntity<ApiResponse> deleteBloodBankProfileImage(Integer bloodBankId);

    Page<BloodBankOptionsAddNetworkDTO> getBloodBankOptionsNetwork(int page, int size, String name);
}
