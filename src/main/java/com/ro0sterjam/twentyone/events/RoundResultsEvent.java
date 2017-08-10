package com.ro0sterjam.twentyone.events;

import com.ro0sterjam.twentyone.table.DealerHand;
import com.ro0sterjam.twentyone.table.PlayerHand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-10.
 */
@RequiredArgsConstructor
public class RoundResultsEvent implements PlayerEvent {

    public enum Result {
        WIN, LOSE, PUSH
    }

    @Getter private final DealerHand dealerHand;
    @Getter private final PlayerHand hand;
    @Getter private final Result result;

}
