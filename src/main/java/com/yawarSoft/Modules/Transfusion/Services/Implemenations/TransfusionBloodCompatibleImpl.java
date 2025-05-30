package com.yawarSoft.Modules.Transfusion.Services.Implemenations;

import com.yawarSoft.Modules.Transfusion.Dto.BloodTypeOption;
import com.yawarSoft.Modules.Transfusion.Enums.RhFactor;
import com.yawarSoft.Modules.Transfusion.Repositories.TransfusionRequestRepository;
import com.yawarSoft.Modules.Transfusion.Services.Interfaces.TransfusionBloodCompatible;
import com.yawarSoft.Modules.Transfusion.Utils.BloodCompatibilityUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransfusionBloodCompatibleImpl implements TransfusionBloodCompatible {

    private final TransfusionRequestRepository transfusionRequestRepository;

    public TransfusionBloodCompatibleImpl(TransfusionRequestRepository transfusionRequestRepository) {
        this.transfusionRequestRepository = transfusionRequestRepository;
    }

    @Override
    public List<String> getBloodTypeCompatibleString(Long idTransfusion) {
        String fullBloodType = getPatientBloodTypeByTransfusion(idTransfusion);
        return BloodCompatibilityUtil.getCompatibleDonors(fullBloodType);
    }

    @Override
    public List<BloodTypeOption> getBloodTypeCompatible(Long idTransfusion) {
        String fullBloodType = getPatientBloodTypeByTransfusion(idTransfusion);

        return BloodCompatibilityUtil.getCompatibleDonors(fullBloodType)
                .stream()
                .map(bt -> new BloodTypeOption(bt, bt))
                .toList();
    }

    private String getPatientBloodTypeByTransfusion(Long idTransfusion) {
        List<Object[]> results = transfusionRequestRepository.findBloodTypeAndRhByTransfusionId(idTransfusion);

        if (results.isEmpty()) {
            throw new IllegalArgumentException("No se encontró información del paciente para la transfusión ID " + idTransfusion);
        }
        Object[] result = results.getFirst();

        String bloodType = (String) result[0];
        String rhName = (String) result[1];
        String rhSymbol = RhFactor.getSymbolByName(rhName);

        return bloodType + rhSymbol;
    }
}
