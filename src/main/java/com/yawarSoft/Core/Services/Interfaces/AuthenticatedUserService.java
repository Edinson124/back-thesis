package com.yawarSoft.Core.Services.Interfaces;

import com.yawarSoft.Core.Entities.UserEntity;

public interface AuthenticatedUserService {
    UserEntity getCurrentUser();
}
