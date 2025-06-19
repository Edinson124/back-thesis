package com.yawarSoft.Modules.Interoperability.Services.Implementations;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import com.yawarSoft.Modules.Interoperability.Dtos.StockResumenDTO;
import com.yawarSoft.Modules.Interoperability.Enums.BloodSNOMED;
import com.yawarSoft.Modules.Interoperability.Enums.UnitTypes;
import com.yawarSoft.Modules.Interoperability.Repositories.BloodBankInteroperabilityRepository;
import com.yawarSoft.Modules.Interoperability.Repositories.UnitInteroperabilityRepository;
import com.yawarSoft.Modules.Interoperability.Services.Interfaces.UnitInteroperabilityService;
import org.hl7.fhir.r4.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UnitInteroperabilityServiceImpl implements UnitInteroperabilityService {
    private final UnitInteroperabilityRepository unitInteroperabilityRepository;
    private  final BloodBankInteroperabilityRepository bloodBankInteroperabilityRepository;

    public UnitInteroperabilityServiceImpl(UnitInteroperabilityRepository unitInteroperabilityRepository, BloodBankInteroperabilityRepository bloodBankInteroperabilityRepository) {
        this.unitInteroperabilityRepository = unitInteroperabilityRepository;
        this.bloodBankInteroperabilityRepository = bloodBankInteroperabilityRepository;
    }


    @Override
    public List<Observation> getStockByBloodBank(String bloodBankId) {
        Integer id = Integer.valueOf(bloodBankId);
        List<StockResumenDTO> resumenDB = unitInteroperabilityRepository.getResumenStockByBloodBank(id);
        BloodBankEntity banco = bloodBankInteroperabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banco de sangre no encontrado"));
        String nombreBanco = banco.getName();

        List<String> bloodTypes = List.of("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-");
        List<String> unitTypes = Arrays.stream(UnitTypes.values())
                .map(UnitTypes::getLabel)
                .toList();

        Map<String, StockResumenDTO> resumenMap = new HashMap<>();
        for (StockResumenDTO dto : resumenDB) {
            String key = dto.getBloodType() + "|" + dto.getUnitType();
            resumenMap.put(key, dto);
        }

        List<StockResumenDTO> resumenFinal = new ArrayList<>();
        for (String blood : bloodTypes) {
            for (String unit : unitTypes) {
                String key = blood + "|" + unit;
                if (resumenMap.containsKey(key)) {
                    resumenFinal.add(resumenMap.get(key));
                } else {
                    resumenFinal.add(new StockResumenDTO(blood, unit, 0L));
                }
            }
        }

        List<Observation> observations = new ArrayList<>();

        for (StockResumenDTO stock : resumenFinal) {
            Observation obs = new Observation();
            obs.setId(IdType.newRandomUuid());
            obs.setStatus(Observation.ObservationStatus.FINAL);
            obs.setCode(new CodeableConcept().setText(stock.getUnitType()));
            obs.setValue(new Quantity().setValue(stock.getQuantity()).setUnit("unidades"));

            // Extraer grupo y Rh del tipo sanguíneo
            String fullType = stock.getBloodType(); // Ej. "O+"
            String bloodGroup = fullType.replace("+", "").replace("-", "");
            boolean isRhPositive = fullType.contains("+");

            BloodSNOMED bloodSNOMED = BloodSNOMED.fromLabel(bloodGroup);

            // Mapear a SNOMED CT
            CodeableConcept bloodGroupConcept = new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://snomed.info/sct")
                            .setCode(bloodSNOMED.getSnomedCode())
                            .setDisplay(bloodSNOMED.getDisplay()))
                    .setText("Grupo sanguíneo " + bloodGroup);

            CodeableConcept rhConcept = new CodeableConcept()
                    .addCoding(new Coding()
                            .setSystem("http://snomed.info/sct")
                            .setCode(isRhPositive ? "165747007" : "165746003")
                            .setDisplay(isRhPositive ? "Rh positivo" : "Rh negativo"))
                    .setText("Factor Rh");

            obs.addComponent(new Observation.ObservationComponentComponent()
                    .setCode(new CodeableConcept().setText("Grupo sanguíneo"))
                    .setValue(bloodGroupConcept));

            obs.addComponent(new Observation.ObservationComponentComponent()
                    .setCode(new CodeableConcept().setText("Factor Rh"))
                    .setValue(rhConcept));

            obs.addPerformer(new Reference("Organization/" + bloodBankId)
                    .setDisplay(nombreBanco));

            observations.add(obs);
        }

        return observations;
    }

}
