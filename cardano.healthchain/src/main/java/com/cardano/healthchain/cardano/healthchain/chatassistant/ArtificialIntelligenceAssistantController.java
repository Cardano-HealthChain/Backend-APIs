package com.cardano.healthchain.cardano.healthchain.chatassistant;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/vc/assistant")
public class ArtificialIntelligenceAssistantController {
    private final ArtificialIntelligenceAssistantService artificialIntelligence;
    public ArtificialIntelligenceAssistantController(ArtificialIntelligenceAssistantService artificialIntelligence) {
        this.artificialIntelligence = artificialIntelligence;
    }
    @PostMapping("chat")
    public ArtificialIntelligenceDTOResponse smartSystemReply(@Valid @RequestBody ArtificialIntelligenceDTORequest artificialIntelligenceDTORequest){
        //TODO
        return null;
    }
}
