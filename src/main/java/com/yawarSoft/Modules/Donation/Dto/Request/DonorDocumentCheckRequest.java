package com.yawarSoft.Modules.Donation.Dto.Request;

public record DonorDocumentCheckRequest(Long donorId,
                                        String documentType,
                                        String documentNumber) {
}
