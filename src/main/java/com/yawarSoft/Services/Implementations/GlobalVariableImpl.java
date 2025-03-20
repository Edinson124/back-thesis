package com.yawarSoft.Services.Implementations;

import com.yawarSoft.Dto.GlobalVariableDTO;
import com.yawarSoft.Dto.GroupedVariablesDTO;
import com.yawarSoft.Entities.GlobalVariablesEntity;
import com.yawarSoft.Mappers.GlobalVariableMapper;
import com.yawarSoft.Repositories.GlobalVariableRepository;
import com.yawarSoft.Services.Interfaces.GlobalVariableService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
        List<GlobalVariablesEntity> variables = globalVariableRepository.findAll();

        Map<String, List<GlobalVariableDTO>> groupedVariables = variables.stream()
                .map(globalVariableMapper::toDTO)
                .collect(Collectors.groupingBy(GlobalVariableDTO::getGroupName));

        return new GroupedVariablesDTO(groupedVariables);
    }

    @Override
    public GlobalVariableDTO editGlobalVariable(Integer id, String value) {
        GlobalVariablesEntity variable = globalVariableRepository.findById(id).get();
        variable.setValue(value);
        globalVariableRepository.save(variable);
        return globalVariableMapper.toDTO(variable);
    }
}
