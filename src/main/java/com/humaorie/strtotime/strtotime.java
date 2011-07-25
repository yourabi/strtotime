package com.humaorie.strtotime;

/*
 * strtotime ${pom.version} (${changeSet} on ${changeSetDate})
 * (c) 2009 Davide Angelocola <davide.angelocola@gmail.com>
 * http://bitbucket.org/dfa/strtotime
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public final class strtotime {

    private static final List<Matcher> matchers;

    static {
        matchers = new LinkedList<Matcher>();
        matchers.add(new NowMatcher());
        matchers.add(new TomorrowMatcher());
        matchers.add(new YesterdayMatcher());
        matchers.add(new DaysMatcher());
        matchers.add(new WeeksMatcher());
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")));
        matchers.add(new DateFormatMatcher(new SimpleDateFormat("yyyy MM dd")));
    }

    public static void registerMatcher(Matcher matcher) {
        matchers.add(0, matcher);
    }

    public static interface Matcher {

        public Date tryConvert(String input);
    }

    public static Date strtotime(String input) {
        for (Matcher matcher : matchers) {
            Date date = matcher.tryConvert(input);

            if (date != null) {
                return date;
            }
        }

        return null;
    }

    private strtotime() {
        throw new UnsupportedOperationException("cannot instantiate");
    }
}
