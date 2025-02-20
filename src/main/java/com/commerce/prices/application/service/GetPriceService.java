package com.commerce.prices.application.service;

import com.commerce.prices.infrastructure.web.exception.PriceNotFoundException;
import com.commerce.prices.application.adapters.PriceMapper;
import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.application.port.in.GetPriceUseCase;
import com.commerce.prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Transactional
public class GetPriceService implements GetPriceUseCase {
    private final PriceRepository priceRepository;

    public GetPriceService(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Override
    public Mono<PriceResponse> getPriceByDateProductAndBrand(final LocalDateTime applicationDate, final Long productId, final Long brandId) {
        return priceRepository.findByProductAndBrandAndDate(productId, brandId, applicationDate)
                .map(PriceMapper::toPriceResponse)
                .switchIfEmpty(Mono.error(new PriceNotFoundException("Price not found")));
    }
}
