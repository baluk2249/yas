package com.yas.cart.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.yas.cart.service.CartService;
import com.yas.cart.viewmodel.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/backoffice/carts")
    public ResponseEntity<List<CartListVm>> listCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }
    
    @GetMapping("/backoffice/carts/{customerId}")
    public ResponseEntity<List<CartGetDetailVm>> listCartDetailByCustomerId(@PathVariable String customerId) {
            return ResponseEntity.ok(cartService.getCartDetailByCustomerId(customerId));
    }

    @GetMapping("/storefront/cart")
    public ResponseEntity<CartGetDetailVm> getLastCart(Principal principal) {
        if(principal == null)
            return ResponseEntity.ok(null);
        return ResponseEntity.ok(cartService.getLastCart());
    }

    @PostMapping(path = "/storefront/carts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CartGetDetailVm.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<CartGetDetailVm> createCart(@Valid @RequestBody CartPostVm cartPostVm, UriComponentsBuilder uriComponentsBuilder)  {
            CartGetDetailVm cartGetDetailVm = cartService.createCart(cartPostVm);
            return ResponseEntity
                    .created(uriComponentsBuilder.replacePath("/carts/{customerId}")
                            .buildAndExpand(cartGetDetailVm.customerId()).toUri())
                    .body(cartGetDetailVm);
    }
}
