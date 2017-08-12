package com.ro0sterjam.twentyone.events;

import com.ro0sterjam.twentyone.table.Hand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-11.
 */
@RequiredArgsConstructor
public class BustedEvent implements PlayerEvent {

    @Getter private final Hand hand;
}
