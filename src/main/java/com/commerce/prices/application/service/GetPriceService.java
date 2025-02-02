package com.commerce.prices.application.service;

import com.commerce.prices.adapter.persistence.PriceMapper;
import com.commerce.prices.application.dto.PriceResponse;
import com.commerce.prices.application.port.in.GetPriceUseCase;
import com.commerce.prices.application.port.out.LoadPricePort;
import com.commerce.prices.domain.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Transactional
public class GetPriceService implements GetPriceUseCase {
    private final LoadPricePort loadPricePort;

    public GetPriceService(final LoadPricePort loadPricePort) {
        this.loadPricePort = loadPricePort;
    }


    @Override
    public Mono<PriceResponse> getPriceByDateProductAndBrand(final LocalDateTime applicationDate, final Long productId, final Long brandId) {
        return loadPricePort.findPrice(applicationDate, productId, brandId)
                .map(PriceMapper::toPriceResponse)
                .switchIfEmpty(Mono.error(new PriceNotFoundException("Price not found")));
    }
}
