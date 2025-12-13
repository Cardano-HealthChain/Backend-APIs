package com.cardano.healthchain.cardano.healthchain.auth;

import com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.WalletAuthService;
import com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/wallet-auth")
public class AuthController {
    private final WalletAuthService walletAuthService;
    public AuthController(WalletAuthService walletAuthService) {
        this.walletAuthService = walletAuthService;
    }
    @GetMapping("/challenge")
    public ResponseEntity<String> getChallenge(@RequestParam String walletAddress) {
        String challenge = walletAuthService.generateChallenge(walletAddress);
        return ResponseEntity.ok(challenge);
    }
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifySignature(@RequestBody WalletSignatureRequest request) {
        boolean verified = walletAuthService.verifySignature(request);
        return ResponseEntity.ok(verified);
    }
    @PostMapping("/wallet-signup")
    public WalletAuthResponse walletSignup(@RequestBody WalletSignUpRequest walletSignUpRequest) {
        return walletAuthService.signup(walletSignUpRequest);
    }
    @PostMapping("/wallet-login")
    public WalletAuthResponse walletLogin(@RequestBody WalletLoginRequest walletLoginRequest) {
        return walletAuthService.login(walletLoginRequest);
    }
    @PostMapping("/link-wallet")
    public WalletAuthResponse linkWallet(@RequestBody LinkWalletRequest linkWalletRequest, Principal principal) {
        return walletAuthService.linkWallet(linkWalletRequest, principal.getName());
    }
}