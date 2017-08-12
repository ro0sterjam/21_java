package com.ro0sterjam.twentyone.rules;

import com.ro0sterjam.twentyone.table.Action;
import com.ro0sterjam.twentyone.table.Hand;

/**
 * Created by kenwang on 2017-08-11.
 */
public interface DealerRules {

    Action nextAction(Hand hand);

}
