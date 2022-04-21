package edu.miu.cs545.waa.controller;

import edu.miu.cs545.waa.dto.FollowDto;
import edu.miu.cs545.waa.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/buyers")
public class BuyerController {
    private SellerService sellerService;

    @Autowired
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PutMapping("/follows")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void followSeller(@RequestBody FollowDto followDto, Principal principal) {
        sellerService.followSeller(followDto, principal.getName());
    }

    @GetMapping("/follows")
    public boolean isFollowed(@RequestParam Long sellerId, Principal principal) {
       return sellerService.isFollowed(sellerId, principal.getName());
    }
}
