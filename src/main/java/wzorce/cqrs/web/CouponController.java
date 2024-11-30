package wzorce.cqrs.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import wzorce.cqrs.application.command.create.CreateCouponCommand;
import wzorce.cqrs.application.command.deactivate.DeactivateCouponCommand;
import wzorce.cqrs.application.command.use.UseCouponCommand;
import wzorce.cqrs.application.query.CouponDetailsDto;
import wzorce.cqrs.application.query.CouponQueryService;
import wzorce.cqrs.common.CommandHandlerExecutor;
import wzorce.cqrs.domain.CouponId;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/coupons")
@AllArgsConstructor
class CouponController {

    private final CommandHandlerExecutor commandHandlerExecutor;
    private final CouponQueryService couponQueryService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody @Valid CreateCouponDto createCouponDto) {
        var couponId = CouponId.newOne();
        var createCouponCommand = new CreateCouponCommand(couponId, createCouponDto.nominalValue());
        commandHandlerExecutor.execute(createCouponCommand);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(couponId.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}/use")
    public ResponseEntity<Void> useCoupon(@PathVariable UUID id) {
        var useCouponCommand = new UseCouponCommand(new CouponId(id));
        commandHandlerExecutor.execute(useCouponCommand);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCoupon(@PathVariable UUID id) {
        var deactivateCouponCommand = new DeactivateCouponCommand(new CouponId(id));
        commandHandlerExecutor.execute(deactivateCouponCommand);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailsDto> getCoupon(@PathVariable UUID couponId) {
        var couponDetails = couponQueryService.getCoupon(new CouponId(couponId));
        return ResponseEntity.ok(couponDetails);
    }
}
