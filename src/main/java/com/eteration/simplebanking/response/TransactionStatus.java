package com.eteration.simplebanking.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatus {

    private String status;
    private String approvalCode;

}
