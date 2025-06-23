package com.yawarSoft.Modules.Admin.Specification;

import com.yawarSoft.Core.Entities.BloodBankEntity;
import org.springframework.data.jpa.domain.Specification;

public class BloodBankSpecifications {

    public static Specification<BloodBankEntity> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<BloodBankEntity> hasRegion(String region) {
        return (root, query, cb) -> {
            if (region == null || region.isBlank()) return cb.conjunction();
            return cb.equal(root.get("region"), region);
        };
    }

    public static Specification<BloodBankEntity> hasProvince(String province) {
        return (root, query, cb) -> {
            if (province == null || province.isBlank()) return cb.conjunction();
            return cb.equal(root.get("province"), province);
        };
    }

    public static Specification<BloodBankEntity> hasDistrict(String district) {
        return (root, query, cb) -> {
            if (district == null || district.isBlank()) return cb.conjunction();
            return cb.equal(root.get("district"), district);
        };
    }

    public static Specification<BloodBankEntity> isInternal(Boolean isInternal) {
        return (root, query, cb) -> {
            if (isInternal == null) return cb.conjunction();
            return cb.equal(root.get("isInternal"), isInternal);
        };
    }
}

