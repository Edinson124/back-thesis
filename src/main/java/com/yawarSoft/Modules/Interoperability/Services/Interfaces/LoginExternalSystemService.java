package com.yawarSoft.Modules.Interoperability.Services.Interfaces;

import com.yawarSoft.Core.Entities.ExternalSystemEntity;

public interface LoginExternalSystemService {
    String obtainToken(ExternalSystemEntity externalSystem);
}
