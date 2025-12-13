package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth;

import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryI;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserModel;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import com.cardano.healthchain.cardano.healthchain.utils.web3walletauth.dtos.*;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletAuthService {

    private final UserRepositoryI userRepository;
    private final WalletRepositoryI walletRepository;
    private final JwtService jwtService;

    public WalletAuthService(UserRepositoryI userRepository, WalletRepositoryI walletRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.jwtService = jwtService;
    }

    public String generateChallenge(String walletAddress) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] nonce = new byte[32];
        secureRandom.nextBytes(nonce);
//        Encode it URL-safe and prepend context
        String challenge = "HealthChain Authentication: " + Base64.getUrlEncoder().withoutPadding().encodeToString(nonce);
//        Store in DB (upsert: one challenge per wallet)
        walletRepository.saveChallenge(walletAddress, challenge, LocalDateTime.now().plusMinutes(30));
        return challenge;
    }

    public WalletAuthResponse signup(WalletSignUpRequest walletSignUpRequest) {
        boolean valid = verifySignature(walletSignUpRequest.getWalletAddress(), walletSignUpRequest.getMessage(),
                walletSignUpRequest.getSignature(), walletSignUpRequest.getPublicKey());
        if (!valid) {
            throw new RuntimeException("Invalid wallet signature");
        }
        // check if wallet already exists and throw exception if exists
        userRepository.existsByWalletAddress(walletSignUpRequest.getWalletAddress());
        // create new minimal user
        UserModel newUser = new UserModel();
        newUser.setWallet_address(walletSignUpRequest.getWalletAddress());
        newUser.setStake_address(walletSignUpRequest.getStakeAddress());
        newUser.setPublic_key(walletSignUpRequest.getPublicKey());
        newUser.setWallet_network("CARDANO");
        newUser.setRole(walletSignUpRequest.getRole());
        newUser.setCreated_at(LocalDateTime.now());
        UUID minimalUserForWalletSignUpUser_id = userRepository.createMinimalUserForWalletSignUp(newUser);
        // generate JWT
        String token = jwtService.generateTokenWithUserId(minimalUserForWalletSignUpUser_id.toString(), walletSignUpRequest.getRole(), Map.of());
        return new WalletAuthResponse(true, token, walletSignUpRequest.getRole(), walletSignUpRequest.getWalletAddress(), "Wallet registered successfully");
    }

    public WalletAuthResponse login(WalletLoginRequest walletLoginRequest) {
        boolean valid = verifySignature(walletLoginRequest.getWalletAddress(), walletLoginRequest.getMessage(),
                walletLoginRequest.getSignature(), walletLoginRequest.getPublicKey());
        if (!valid) {
            throw new RuntimeException("Invalid wallet signature");
        }
        UserModel user = userRepository.findByWalletAddress(walletLoginRequest.getWalletAddress())
                .orElseThrow(() -> new RuntimeException("Wallet not registered"));
        String token = jwtService.generateTokenWithUserId(user.getUser_id().toString(), user.getRole(), Map.of());
        return new WalletAuthResponse(true, token, user.getRole(), user.getWallet_address(), "Login successful");
    }

    public WalletAuthResponse linkWallet(LinkWalletRequest linkWalletRequest, String user_id) {
        boolean valid = verifySignature(linkWalletRequest.getWalletAddress(), linkWalletRequest.getMessage(),
                linkWalletRequest.getSignature(), linkWalletRequest.getPublicKey());
        if (!valid) {
            throw new RuntimeException("Invalid wallet signature");
        }
        //check if wallet already exists and throw exception if it does
        userRepository.existsByWalletAddress(linkWalletRequest.getWalletAddress());
        UserModel currentUser = userRepository.getUserById(user_id);
        currentUser.setWallet_address(linkWalletRequest.getWalletAddress());
        currentUser.setStake_address(linkWalletRequest.getStakeAddress());
        currentUser.setPublic_key(linkWalletRequest.getPublicKey());
        userRepository.updateWalletInfo(currentUser);
        return new WalletAuthResponse(true, null, currentUser.getRole(), linkWalletRequest.getWalletAddress(), "Wallet linked successfully");
    }

    // Placeholder signature verification
    public boolean verifySignature(String walletAddress, String message, String signature, String publicKey) {
//        Retrieve stored challenge
        Optional<String> storedChallengeOpt = walletRepository.getChallenge(walletAddress);
        if (storedChallengeOpt.isEmpty()) {
            throw new RuntimeException("Challenge Expired");
        }
        String storedChallenge = storedChallengeOpt.get();
//        Check that message matches stored challenge
        if (!storedChallenge.equals(message)) {
            throw new RuntimeException("Challenge Mismatch");
        }

//        Decode signature and public key from Base64
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] signatureBytes;
        byte[] publicKeyBytes;
        try {
            signatureBytes = Base64.getDecoder().decode(signature);
            publicKeyBytes = Base64.getDecoder().decode(publicKey);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid wallet signature");
        }

//        Verify signature using Bouncy Castle Ed25519
        try {
            Ed25519PublicKeyParameters pubKeyParam = new Ed25519PublicKeyParameters(publicKeyBytes, 0);
            Ed25519Signer signer = new Ed25519Signer();
            signer.init(false, pubKeyParam); // false = verifying
            signer.update(messageBytes, 0, messageBytes.length);
            boolean verified = signer.verifySignature(signatureBytes);
            if (verified) {
//                Remove challenge to prevent replay
                walletRepository.deleteChallenge(walletAddress);
            }
            return verified;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verifySignature(WalletSignatureRequest request) {
//        Retrieve stored challenge
        Optional<String> storedChallengeOpt = walletRepository.getChallenge(request.getWalletAddress());
        if (storedChallengeOpt.isEmpty()) {
            return false;
        }
        String storedChallenge = storedChallengeOpt.get();
//        Check that message matches stored challenge
        if (!storedChallenge.equals(request.getMessage())) {
            return false;
        }

//        Decode signature and public key from Base64
        byte[] messageBytes = request.getMessage().getBytes(StandardCharsets.UTF_8);
        byte[] signatureBytes;
        byte[] publicKeyBytes;
        try {
            signatureBytes = Base64.getDecoder().decode(request.getSignature());
            publicKeyBytes = Base64.getDecoder().decode(request.getPublicKey());
        } catch (IllegalArgumentException e) {
            return false;
        }

//        Verify signature using Bouncy Castle Ed25519
        try {
            Ed25519PublicKeyParameters pubKeyParam = new Ed25519PublicKeyParameters(publicKeyBytes, 0);
            Ed25519Signer signer = new Ed25519Signer();
            signer.init(false, pubKeyParam); // false = verifying
            signer.update(messageBytes, 0, messageBytes.length);
            boolean verified = signer.verifySignature(signatureBytes);
            if (verified) {
//                Remove challenge to prevent replay
                walletRepository.deleteChallenge(request.getWalletAddress());
            }
            return verified;
        } catch (Exception e) {
            return false;
        }
    }
}