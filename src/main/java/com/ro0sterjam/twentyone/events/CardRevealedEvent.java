package com.ro0sterjam.twentyone.events;

import com.ro0sterjam.twentyone.table.Card;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-09.
 */
@RequiredArgsConstructor
public class CardRevealedEvent implements GlobalEvent {

    @Getter private final Card card;

}
