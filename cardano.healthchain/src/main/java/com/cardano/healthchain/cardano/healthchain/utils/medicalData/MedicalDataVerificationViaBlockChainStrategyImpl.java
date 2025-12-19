package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainServiceI;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MedicalDataVerificationViaBlockChainStrategyImpl implements MedicalDataVerificationStrategyI{
    private final BlockChainServiceI cardanoBlockChainImpl;
    public MedicalDataVerificationViaBlockChainStrategyImpl(BlockChainServiceI cardanoBlockChainImpl) {
        this.cardanoBlockChainImpl = cardanoBlockChainImpl;
    }
    @Override
    public ArrayList<MedicalDataResponse> verifyRecords(ArrayList<MedicalDataResponse> medicalDataResponseArrayList) {
        // basic version for now, later verify via the blockchain
        ArrayList<MedicalDataResponse> checkedMedicalData = new ArrayList<>();
        for(MedicalDataResponse medicalDataResponse : medicalDataResponseArrayList){
            //use blockchain to actually check later and set response to false if hash is missing on blockchain
            medicalDataResponse.setVerified(true);
            checkedMedicalData.add(medicalDataResponse);
        }
        return checkedMedicalData;
    }
}