package wzorce.cqrs.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wzorce.cqrs.application.CouponService;
import wzorce.cqrs.application.dto.CouponDetailsDto;
import wzorce.cqrs.application.dto.CreateCouponDto;
import wzorce.cqrs.domain.CouponId;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupons")
@AllArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody @Valid CreateCouponDto createCouponDto) {
        var couponId = couponService.createCoupon(createCouponDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(couponId.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<Void> useCoupon(@PathVariable UUID id) {
        couponService.useCoupon(new CouponId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCoupon(@PathVariable UUID id) {
        couponService.deactivateCoupon(new CouponId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailsDto> getCoupon(@PathVariable UUID couponId) {
        var couponDetails = couponService.getCoupon(new CouponId(couponId));
        return ResponseEntity.ok(couponDetails);
    }
}
