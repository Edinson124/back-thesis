package com.yawarSoft.Modules.Admin.Services.Implemenations;

import com.yawarSoft.Modules.Admin.Dto.GlobalVariableDTO;
import com.yawarSoft.Modules.Admin.Dto.GroupedVariablesDTO;
import com.yawarSoft.Core.Entities.GlobalVariablesEntity;
import com.yawarSoft.Modules.Admin.Mappers.GlobalVariableMapper;
import com.yawarSoft.Modules.Admin.Repositories.GlobalVariableRepository;
import com.yawarSoft.Modules.Admin.Services.Interfaces.GlobalVariableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GlobalVariableImpl implements GlobalVariableService {

    private final GlobalVariableRepository globalVariableRepository;
    private final GlobalVariableMapper globalVariableMapper;

    public GlobalVariableImpl(GlobalVariableRepository globalVariableRepository, GlobalVariableMapper globalVariableMapper) {
        this.globalVariableRepository = globalVariableRepository;
        this.globalVariableMapper = globalVariableMapper;
    }

    @Override
    public GroupedVariablesDTO getAllGlobalVariablesGrouped() {
        List<GlobalVariablesEntity> variables = globalVariableRepository.findAllByOrderByIdAsc();

        Map<String, List<GlobalVariableDTO>> groupedVariables = variables.stream()
                .map(globalVariableMapper::toDTO)
                .collect(Collectors.groupingBy(GlobalVariableDTO::getGroupName));

        return new GroupedVariablesDTO(groupedVariables);
    }

    @Override
    public GlobalVariableDTO editGlobalVariable(Integer id, String value) {
        GlobalVariablesEntity variable = globalVariableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Variable global no encontrada con ID: " + id));

        variable.setValue(value);
        globalVariableRepository.save(variable);

        return globalVariableMapper.toDTO(variable);
    }

    @Override
    public GlobalVariableDTO getByCode(String code) {
        GlobalVariablesEntity variable = globalVariableRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Variable global no encontrada con code: " + code));
        return globalVariableMapper.toDTO(variable);
    }
}
