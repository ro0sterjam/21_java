package com.ro0sterjam.twentyone.events;

import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.Hand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-10.
 */
@RequiredArgsConstructor
public class PlayerDealEvent implements PlayerEvent {

    @Getter private final DealerHand dealerHand;
    @Getter private final Hand hand;
}
