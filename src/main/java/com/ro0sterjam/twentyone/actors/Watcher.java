package com.ro0sterjam.twentyone.actors;

import com.ro0sterjam.twentyone.events.GlobalEvent;
import com.ro0sterjam.twentyone.events.PlayerEvent;

/**
 * Created by kenwang on 2017-08-09.
 */
public interface Watcher {

    void onGlobalEvent(GlobalEvent event);

    void onPlayerEvent(PlayerEvent event);
}
