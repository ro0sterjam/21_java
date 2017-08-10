package com.ro0sterjam.twentyone.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by kenwang on 2017-08-09.
 */
@RequiredArgsConstructor
public class LoadShoeEvent implements GlobalEvent {

    @Getter
    private final int numOfDecks;

}
