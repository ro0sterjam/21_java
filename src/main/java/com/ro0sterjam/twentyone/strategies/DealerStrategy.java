package com.ro0sterjam.twentyone.strategies;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

/**
 * Created by kenwang on 2017-08-09.
 */
public interface DealerStrategy {

    Action nextAction(Hand hand);

}
